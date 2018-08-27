package ar.com.american118models.rest.request;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextosPaginaRequest
{
	@XmlElement
	private String pagina;
	@XmlElement
	private String idioma;
	@XmlElement
	private List<TextoPaginaRequest> textosPagina;
	@XmlElement
	private String token;

	public String getPagina()
	{
		return pagina;
	}

	public void setPagina(String pagina)
	{
		this.pagina = pagina;
	}

	public String getIdioma()
	{
		return idioma;
	}

	public void setIdioma(String idioma)
	{
		this.idioma = idioma;
	}
	
	public List<TextoPaginaRequest> getTextosPagina()
	{
		return textosPagina;
	}

	public void setTextosPagina(List<TextoPaginaRequest> textosPagina)
	{
		this.textosPagina = textosPagina;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
}
