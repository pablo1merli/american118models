package ar.com.american118models.modelo.entidades.idiomas;

import org.springframework.data.annotation.Id;

public class Idioma
{
	@Id
	private String id;

	private String abreviatura;
	private String nombre;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

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
