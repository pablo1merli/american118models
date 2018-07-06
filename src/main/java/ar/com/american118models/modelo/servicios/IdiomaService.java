package ar.com.american118models.modelo.servicios;

import java.util.List;

import ar.com.american118models.modelo.entidades.idiomas.Idioma;

public interface IdiomaService
{
	public abstract List<Idioma> getIdiomas();
	public abstract boolean altaIdioma(Idioma idioma);
	public abstract boolean bajaIdioma(Idioma idioma);
	public abstract Idioma modificarIdioma(Idioma idioma);
}
