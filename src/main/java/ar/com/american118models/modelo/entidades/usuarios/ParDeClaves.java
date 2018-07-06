package ar.com.american118models.modelo.entidades.usuarios;

import org.springframework.data.annotation.Id;

public class ParDeClaves
{
	@Id
	private String id;

	private byte[] privateKey;
	private byte[] publicKey;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public byte[] getPrivateKey()
	{
		return privateKey;
	}

	public void setPrivateKey(byte[] privateKey)
	{
		this.privateKey = privateKey;
	}

	public byte[] getPublicKey()
	{
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey)
	{
		this.publicKey = publicKey;
	}
}
