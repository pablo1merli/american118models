package ar.com.american118models.modelo.servicios;

import java.util.List;

import ar.com.american118models.modelo.entidades.paginas.TextosPagina;

public interface TextosPaginaService
{
	public abstract List<TextosPagina> getTextosPagina();
	public abstract TextosPagina getTextosPaginaPorIdioma(String pagina, String idioma);
}
