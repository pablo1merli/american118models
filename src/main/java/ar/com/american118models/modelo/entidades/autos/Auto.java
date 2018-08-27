package ar.com.american118models.modelo.entidades.autos;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Auto
{
	@Id
	private String id;

	private DatosAuto datosAuto;
	private boolean loTengo;
	private String fabricante;
	private String serie;
	private int cantidadProducida;
	private String edicionEspecial;
	private int anioFabricacion;
	private String urlRest;
	private List<Review> reviews;
	private List<String> urlFotos;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public DatosAuto getDatosAuto()
	{
		return datosAuto;
	}

	public void setDatosAuto(DatosAuto datosAuto)
	{
		this.datosAuto = datosAuto;
	}

	public boolean isLoTengo()
	{
		return loTengo;
	}

	public void setLoTengo(boolean loTengo)
	{
		this.loTengo = loTengo;
	}

	public String getFabricante()
	{
		return fabricante;
	}

	public void setFabricante(String fabricante)
	{
		this.fabricante = fabricante;
	}

	public String getSerie()
	{
		return serie;
	}

	public void setSerie(String serie)
	{
		this.serie = serie;
	}

	public int getCantidadProducida()
	{
		return cantidadProducida;
	}

	public void setCantidadProducida(int cantidadProducida)
	{
		this.cantidadProducida = cantidadProducida;
	}

	public String getEdicionEspecial()
	{
		return edicionEspecial;
	}

	public void setEdicionEspecial(String edicionEspecial)
	{
		this.edicionEspecial = edicionEspecial;
	}

	public int getAnioFabricacion()
	{
		return anioFabricacion;
	}

	public void setAnioFabricacion(int anioFabricacion)
	{
		this.anioFabricacion = anioFabricacion;
	}

	public String getUrlRest()
	{
		return urlRest;
	}

	public void setUrlRest(String urlRest)
	{
		this.urlRest = urlRest;
	}

	public List<Review> getReviews()
	{
		return reviews;
	}

	public void setReviews(List<Review> reviews)
	{
		this.reviews = reviews;
	}

	public List<String> getUrlFotos()
	{
		return urlFotos;
	}

	public void setUrlFotos(List<String> urlFotos)
	{
		this.urlFotos = urlFotos;
	}
}
