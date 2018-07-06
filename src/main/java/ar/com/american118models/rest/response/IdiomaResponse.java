package ar.com.american118models.rest.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdiomaResponse
{
	@XmlElement
	private String abreviatura;
	@XmlElement
	private String nombre;

	public String getAbreviatura()
	{
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura)
	{
		this.abreviatura = abreviatura;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
}
