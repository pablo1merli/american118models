package ar.com.american118models.modelo.servicios.impl;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.jose.jws.JWSBuilder;
import org.jboss.resteasy.jose.jws.JWSInput;
import org.jboss.resteasy.jose.jws.crypto.RSAProvider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ar.com.american118models.modelo.entidades.usuarios.Administrador;
import ar.com.american118models.modelo.entidades.usuarios.ParDeClaves;
import ar.com.american118models.modelo.repositorios.AdministradorRepository;
import ar.com.american118models.modelo.repositorios.ParDeClavesRepository;
import ar.com.american118models.modelo.servicios.AdministradorService;
import ar.com.american118models.utilidades.cifrado.ClavesDeLaAplicacion;

@Service
public class AdministradorServiceImpl implements AdministradorService
{
	@Autowired
	AdministradorRepository administradorRepository;
	@Autowired
	ParDeClavesRepository parDeClavesRepository;
	@Autowired
	ClavesDeLaAplicacion clavesDeLaAplicacion;

	public boolean isExisteAdministrador()
	{
		List<Administrador> listaAdministradores = administradorRepository.findAll();

		return (listaAdministradores.size() > 0) ? true : false;
	}

	public String loginAdministrador(String usuario, String password)
	{
		Administrador administradorLogin = new Administrador();
		administradorLogin.setUsuario(usuario);
		administradorLogin.setPassword(password);

		// Se chequea para que no se invoque desde afuera
		// Si no existe administrador, se crea. Caso contrario se obtiene
		// verifica contra la BD
		if (!this.isExisteAdministrador())
		{
			administradorLogin.setPosicionClave(clavesDeLaAplicacion.convertirHoraEnPosicion(Calendar.getInstance()));
			String claveDeCifrado = clavesDeLaAplicacion.getClave(administradorLogin.getPosicionClave());

			// Cifro la password del administrador de acuerdo a la clave que se
			// obtuvo desde la BD
			administradorLogin.setPassword(clavesDeLaAplicacion.cifrar(administradorLogin.getPassword(), claveDeCifrado));

			// Guardo el administrador en la BD
			administradorRepository.save(administradorLogin);

			// Blanqueo la password para seguir con el proceso de login
			// post-alta de usuario
			administradorLogin.setPassword(password);
		}

		Administrador administradorBD = administradorRepository.findByUsuario(administradorLogin.getUsuario());
		// Verifico si el usuario que hizo login es el administrador ya cargado
		// en la BD
		if (administradorBD == null)
		{
			// No existe el usuario, devuelvo false
			return null;
		}

		String claveAdministradorBD = clavesDeLaAplicacion.getClave(administradorBD.getPosicionClave());
		administradorLogin.setPassword(clavesDeLaAplicacion.cifrar(administradorLogin.getPassword(), claveAdministradorBD));
		// Verifico que la clave de login y la ya guardada en la base, ambas
		// cifradas con la misma clave, coincidan
		if (!administradorLogin.getPassword().equals(administradorBD.getPassword()))
		{
			// No coinciden las claves, devuelvo false
			return null;
		}

		// Cambio el estado en la BD por el de estar autenticado
		administradorLogin = this.cambiarEstado(null, administradorLogin.getUsuario(), true);

		return generarTokenJWT(administradorLogin);
	}

	public Administrador cambiarEstado(String token, String usuario, boolean estaAutenticado)
	{
		if (this.isExisteAdministrador())
		{
			Administrador administradorLogin = null;

			if (token != null)
			{
				administradorLogin = this.obtenerAdministradorDesdeToken(token);
			}
			else if (usuario != null)
			{
				administradorLogin = new Administrador();
				administradorLogin.setUsuario(usuario);
			}

			if (administradorLogin != null)
			{
				administradorLogin.setUsuario(administradorLogin.getUsuario());
				Administrador administradorBD = administradorRepository.findByUsuario(administradorLogin.getUsuario());

				// Verifico si el usuario que hizo login es el administrador ya
				// cargado en la BD
				if (administradorBD != null)
				{
					administradorBD.setEstaAutenticado(estaAutenticado);
					administradorBD = administradorRepository.save(administradorBD);

					return administradorBD;
				}
			}
		}

		return null;
	}

	public boolean controlarAdministradorAutenticadoDesdeToken(String token)
	{
		boolean estaAutenticado = false;

		Administrador administradorDelToken = this.obtenerAdministradorDesdeToken(token);

		if (administradorDelToken != null)
		{
			// Verifico que el administrador del token se encuentre autenticado
			Administrador administradorBD = administradorRepository.findByUsuario(administradorDelToken.getUsuario());
			if (administradorBD.isEstaAutenticado())
			{
				estaAutenticado = true;
			}
		}

		return estaAutenticado;
	}

	private Administrador obtenerAdministradorDesdeToken(String token)
	{
		Administrador administradorDelToken = null;
		Gson gson = new Gson();

		try
		{
			// Obtengo el administrador del Token JWS
			JWSInput jWSInput = new JWSInput(token, ResteasyProviderFactory.getInstance());

			List<ParDeClaves> listaParDeClaves = (List<ParDeClaves>)parDeClavesRepository.findAll();
			ParDeClaves parDeClaves = listaParDeClaves.get(0); 
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(parDeClaves.getPrivateKey()));
	        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(parDeClaves.getPublicKey()));
			KeyPair keyPair = new KeyPair(publicKey, privateKey);
			
			if (RSAProvider.verify(jWSInput, keyPair.getPublic()))
			{
				String administradorJSONString = (String) jWSInput.readContent(String.class, null, null, MediaType.TEXT_PLAIN_TYPE);
				administradorDelToken = gson.fromJson(administradorJSONString, Administrador.class);
			}
		}
		catch (NoSuchAlgorithmException nsae)
		{
		
		}
		catch (InvalidKeySpecException ikse)
		{
			
		}

		return administradorDelToken;
	}

	private String generarTokenJWT(Administrador administrador)
	{
		String token = null;

		try
		{
			Gson gson = new Gson();
			KeyPair keyPair = null;
			List<ParDeClaves> listaKeyPair = (List<ParDeClaves>)parDeClavesRepository.findAll();
			if (listaKeyPair.size() == 0)
			{
				ParDeClaves parDeClaves = new ParDeClaves();
				KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
				keyGen.initialize(2048, random);

				keyPair = keyGen.generateKeyPair();
				parDeClaves.setPrivateKey(keyPair.getPrivate().getEncoded());
				parDeClaves.setPublicKey(keyPair.getPublic().getEncoded());
				 
				parDeClavesRepository.save(parDeClaves);
			}
			else if (listaKeyPair.size() > 0)
			{
				ParDeClaves parDeClaves = listaKeyPair.get(0);
		        KeyFactory kf = KeyFactory.getInstance("RSA");
		        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(parDeClaves.getPrivateKey()));
		        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(parDeClaves.getPublicKey()));
				keyPair = new KeyPair(publicKey, privateKey);
			}
			
			String administradorJSONString = gson.toJson(administrador);
			
			token = new JWSBuilder().contentType(MediaType.TEXT_PLAIN_TYPE).content(administradorJSONString, MediaType.TEXT_PLAIN_TYPE).rsa256(keyPair.getPrivate());
		}
		catch (NoSuchAlgorithmException nsae)
		{

		}
		catch (NoSuchProviderException nsp)
		{

		}
		catch (InvalidKeySpecException ikse)
		{
			
		}

		return token;
	}
}
