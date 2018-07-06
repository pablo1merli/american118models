package ar.com.american118models.modelo.entidades.usuarios;

import org.springframework.data.annotation.Id;

public class Administrador
{
	@Id
	private String id;

	private String usuario;
	private String password;
	private int posicionClave;
	private boolean estaAutenticado;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getPosicionClave()
	{
		return posicionClave;
	}

	public void setPosicionClave(int posicionClave)
	{
		this.posicionClave = posicionClave;
	}

	public boolean isEstaAutenticado()
	{
		return estaAutenticado;
	}

	public void setEstaAutenticado(boolean estaAutenticado)
	{
		this.estaAutenticado = estaAutenticado;
	}
}
