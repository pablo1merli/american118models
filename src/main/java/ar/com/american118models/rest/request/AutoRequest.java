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
public class AutoRequest
{
	@XmlElement
	private String id;
	@XmlElement
	private DatosAutoRequest datosAuto;
	@XmlElement
	private boolean loTengo;
	@XmlElement
	private String fabricante;
	@XmlElement
	private String serie;
	@XmlElement
	private int cantidadProducida;
	@XmlElement
	private String edicionEspecial;
	@XmlElement
	private int anioFabricacion;
	@XmlElement
	private String urlRest;
	@XmlElement
	private List<ReviewRequest> reviews;
	@XmlElement
	private List<String> urlFotos;
	@XmlElement
	private String token;

	public String getId() 
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public DatosAutoRequest getDatosAuto()
	{
		return datosAuto;
	}

	public void setDatosAuto(DatosAutoRequest datosAuto)
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

	public List<ReviewRequest> getReviews()
	{
		return reviews;
	}

	public void setReviews(List<ReviewRequest> reviews)
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

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
}
