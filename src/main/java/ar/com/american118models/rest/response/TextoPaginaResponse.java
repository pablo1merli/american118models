package ar.com.american118models.rest.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextoPaginaResponse
{
	@XmlElement
	private String idioma;
	@XmlElement
	private String tag;
	@XmlElement
	private String texto;

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public String getTexto()
	{
		return texto;
	}

	public void setTexto(String texto)
	{
		this.texto = texto;
	}

	public String getIdioma() 
	{
		return idioma;
	}

	public void setIdioma(String idioma) 
	{
		this.idioma = idioma;
	}
	
}
