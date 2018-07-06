package ar.com.american118models.modelo.entidades.paginas;

import java.util.List;

import org.springframework.data.annotation.Id;

public class TextosPagina
{
	@Id
	private String id;

	private String pagina;
	private String idioma;
	private List<TextoPagina> textosPagina;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

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

	public List<TextoPagina> getTextosPagina()
	{
		return textosPagina;
	}

	public void setTextosPagina(List<TextoPagina> textosPagina)
	{
		this.textosPagina = textosPagina;
	}
}
