package ar.com.american118models.utilidades.cifrado;

import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.american118models.configuracion.Configuracion;
import ar.com.american118models.modelo.entidades.usuarios.Clave;
import ar.com.american118models.modelo.entidades.usuarios.ConfiguracionBD;
import ar.com.american118models.modelo.repositorios.ClaveRepository;
import ar.com.american118models.modelo.repositorios.ConfiguracionBDRepository;

@Component
public class ClavesDeLaAplicacion
{
	private final Configuracion configuracion;

	@Autowired
	private AES aes;
	@Autowired
	private ConfiguracionBDRepository configuracionBDRepository;
	@Autowired
	private ClaveRepository claveRepository;

	@Autowired
	public ClavesDeLaAplicacion(Configuracion configuracion)
	{
		this.configuracion = configuracion;
	}

	public void generarClaves()
	{
		int cantidadClaves = Integer.valueOf(configuracion.getValorDeProperty("seguridad.claves.cantidad"));
		int longitudClave = Integer.valueOf(configuracion.getValorDeProperty("seguridad.clave.longitud"));
		String nombreClave = configuracion.getValorDeProperty("seguridad.claves.nombreClave");
		String nombreVectorInicializacion = configuracion.getValorDeProperty("seguridad.claves.vectorInicializacion");
		String valorClavePrincipal = null;
		String vectorInicializacion = null;
		
		// Si no hay clave, la genero
		if (configuracionBDRepository.findByClave(nombreClave) == null)
		{
			valorClavePrincipal = RandomStringUtils.randomAlphanumeric(longitudClave);
			
			vectorInicializacion = RandomStringUtils.randomAlphanumeric(longitudClave);
			
			configuracionBDRepository.save(new ConfiguracionBD(nombreClave, valorClavePrincipal));
			configuracionBDRepository.save(new ConfiguracionBD(nombreVectorInicializacion, vectorInicializacion));
		}
		
		if (claveRepository.findAll().size() == 0)
		{
			valorClavePrincipal = ((ConfiguracionBD)configuracionBDRepository.findByClave(nombreClave)).getValor();
			vectorInicializacion = ((ConfiguracionBD)configuracionBDRepository.findByClave(nombreVectorInicializacion)).getValor();
			
			String vectorDeClaves[] = new String[cantidadClaves];

			for (int i = 0; i < cantidadClaves; i++)
			{
				vectorDeClaves[i] = aes.cifrar(RandomStringUtils.randomAlphanumeric(longitudClave), valorClavePrincipal, vectorInicializacion);
			}

			cambiarOrder(vectorDeClaves, cantidadClaves);
			
			for (int i = 0; i < cantidadClaves; i++)
			{
				claveRepository.save(new Clave(i, vectorDeClaves[i]));
			}
		}
	}
	
	public int convertirHoraEnPosicion(Calendar hora)
	{
		return ((24 * hora.get(Calendar.HOUR_OF_DAY) - 1) + hora.get(Calendar.MINUTE));
	}
	
	public String getClave(int posicion)
	{
		// Se ejecuta solamente cuando no hay claves cargadas en la base de datos
		this.generarClaves();

		String nombreClave = configuracion.getValorDeProperty("seguridad.claves.nombreClave");
		String valorClavePrincipal = ((ConfiguracionBD)configuracionBDRepository.findByClave(nombreClave)).getValor();
		String nombreVectorInicializacion = configuracion.getValorDeProperty("seguridad.claves.vectorInicializacion");
		String vectorInicializacion = ((ConfiguracionBD)configuracionBDRepository.findByClave(nombreVectorInicializacion)).getValor();
		
		String clave = ((Clave)claveRepository.findByPosicion(posicion)).getClave(); 
		
		return aes.descifrar(clave, valorClavePrincipal, vectorInicializacion);
	}
	
	public String cifrar(String textoACifrar, String clave)
	{
		String nombreVectorInicializacion = configuracion.getValorDeProperty("seguridad.claves.vectorInicializacion");
		String vectorInicializacion = ((ConfiguracionBD)configuracionBDRepository.findByClave(nombreVectorInicializacion)).getValor();
		
		return aes.cifrar(textoACifrar, clave, vectorInicializacion);
	}

	private void cambiarOrder(String[] vectorDeClaves, int cantidadClaves)
	{
		String aux = null;
		Random random = new Random();

		for (int i = 0; i < cantidadClaves; i++)
		{
			int posicionRandom = random.nextInt(cantidadClaves);

			aux = vectorDeClaves[i];
			vectorDeClaves[i] = vectorDeClaves[posicionRandom];
			vectorDeClaves[posicionRandom] = aux;
		}
	}
}
