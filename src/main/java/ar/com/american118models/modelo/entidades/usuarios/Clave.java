package ar.com.american118models.modelo.entidades.usuarios;

import org.springframework.data.annotation.Id;

public class Clave
{
	@Id
	private String id;

	private int posicion;
	private String clave;

	public Clave(int posicion, String clave)
	{
		this.posicion = posicion;
		this.clave = clave;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getPosicion()
	{
		return posicion;
	}

	public void setPosicion(int posicion)
	{
		this.posicion = posicion;
	}

	public String getClave()
	{
		return clave;
	}

	public void setClave(String clave)
	{
		this.clave = clave;
	}

}
