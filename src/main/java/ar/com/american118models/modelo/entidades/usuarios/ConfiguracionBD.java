package ar.com.american118models.modelo.entidades.usuarios;

import org.springframework.data.annotation.Id;

public class ConfiguracionBD
{
	@Id
	private String id;

	private String clave;
	private String valor;

	public ConfiguracionBD(String clave, String valor)
	{
		this.clave = clave;
		this.valor = valor;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getClave()
	{
		return clave;
	}

	public void setClave(String clave)
	{
		this.clave = clave;
	}

	public String getValor()
	{
		return valor;
	}

	public void setValor(String valor)
	{
		this.valor = valor;
	}

}
