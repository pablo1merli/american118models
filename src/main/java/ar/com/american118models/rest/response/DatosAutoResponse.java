package ar.com.american118models.rest.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosAutoResponse
{
	@XmlElement
	private String marca;
	@XmlElement
	private String modelo;
	@XmlElement
	private int anio;
	@XmlElement
	private String color;
	@XmlElement
	private String motor;
	@XmlElement
	private int potencia;
	@XmlElement
	private int ordenamiento;

	public String getMarca()
	{
		return marca;
	}

	public void setMarca(String marca)
	{
		this.marca = marca;
	}

	public String getModelo()
	{
		return modelo;
	}

	public void setModelo(String modelo)
	{
		this.modelo = modelo;
	}

	public int getAnio()
	{
		return anio;
	}

	public void setAnio(int anio)
	{
		this.anio = anio;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public String getMotor()
	{
		return motor;
	}

	public void setMotor(String motor)
	{
		this.motor = motor;
	}

	public int getPotencia()
	{
		return potencia;
	}

	public void setPotencia(int potencia)
	{
		this.potencia = potencia;
	}

	public int getOrdenamiento()
	{
		return ordenamiento;
	}

	public void setOrdenamiento(int ordenamiento)
	{
		this.ordenamiento = ordenamiento;
	}
}
