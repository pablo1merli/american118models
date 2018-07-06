package ar.com.american118models.utilidades.cifrado;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class AES
{
	public String cifrar(String textoACifrar, String clave, String vectorInicializacion)
	{
		return aplicarCifrado(textoACifrar, Cipher.ENCRYPT_MODE, clave, vectorInicializacion);
	}

	public String descifrar(String textoADescifrar, String clave, String vectorInicializacion)
	{
		return aplicarCifrado(textoADescifrar, Cipher.DECRYPT_MODE, clave, vectorInicializacion);
	}

	private String aplicarCifrado(String texto, int operacion, String clave, String vectorInicializacion)
	{
		byte[] textoCalculado = null;

		
		try
		{
			IvParameterSpec ivParameterSpec = new IvParameterSpec(vectorInicializacion.getBytes());
			
			Key key = generarClave(clave);
			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			aesCipher.init(operacion, key, ivParameterSpec);
			
			if (operacion == Cipher.ENCRYPT_MODE)
			{
				byte[] textoCifrado = aesCipher.doFinal(texto.getBytes()); 
				textoCalculado = Base64.encodeBase64(textoCifrado);
			}
			else
			{
				byte[] textoCifradoSinB64 = Base64.decodeBase64(texto.getBytes());
				textoCalculado = aesCipher.doFinal(textoCifradoSinB64);
			}
				
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new String(textoCalculado);
	}

	private Key generarClave(String clave)
	{
		byte[] claveBytes = clave.getBytes();
		Key key = new SecretKeySpec(claveBytes, "AES");

		return key;
	}
}
