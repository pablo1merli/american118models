package ar.com.american118models.rest.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextosPaginaResponse
{
	@XmlElement
	private String idioma;
	@XmlElement
	private String pagina;
	@XmlElement
	private List<TextoPaginaResponse> textosPagina;
	
	public String getIdioma() 
	{
		return idioma;
	}
	
	public void setIdioma(String idioma) 
	{
		this.idioma = idioma;
	}
	
	public String getPagina() 
	{
		return pagina;
	}
	
	public void setPagina(String pagina) 
	{
		this.pagina = pagina;
	}
	
	public List<TextoPaginaResponse> getTextosPagina() 
	{
		return textosPagina;
	}
	
	public void setTextosPagina(List<TextoPaginaResponse> textosPagina) 
	{
		this.textosPagina = textosPagina;
	}

	
}
