package ar.com.american118models.mapping.converters.desdeRequestAModelo;

import java.util.ArrayList;
import java.util.List;

import org.dozer.CustomConverter;

import ar.com.american118models.modelo.entidades.autos.Auto;
import ar.com.american118models.modelo.entidades.autos.DatosAuto;
import ar.com.american118models.modelo.entidades.autos.Review;
import ar.com.american118models.modelo.entidades.idiomas.Idioma;
import ar.com.american118models.modelo.entidades.paginas.TextoPagina;
import ar.com.american118models.modelo.entidades.paginas.TextosPagina;
import ar.com.american118models.rest.request.AutoRequest;
import ar.com.american118models.rest.request.IdiomaRequest;
import ar.com.american118models.rest.request.ReviewRequest;
import ar.com.american118models.rest.request.TextoPaginaRequest;
import ar.com.american118models.rest.request.TextosPaginaRequest;

public class DesdeRequestAModeloConverter implements CustomConverter
{

	@Override
	public Object convert(Object destino, Object origen, Class<?> claseDestino, Class<?> claseOrigen)
	{
		Object respuesta = null;

		if (origen == null)
		{
			return null;
		}

		if (origen instanceof AutoRequest)
		{
			AutoRequest origenCasted = (AutoRequest) origen;
			Auto auto = new Auto();
			List<Review> reviewList = new ArrayList<Review>();
			DatosAuto datosAuto = new DatosAuto();

			if (origenCasted.getReviews() != null)
			{
				for (ReviewRequest reviewRequest: origenCasted.getReviews())
				{
					Review review = new Review();
					
					review.setIdioma(reviewRequest.getIdioma());
					review.setReview(reviewRequest.getReview());
					review.setFecha(reviewRequest.getFecha());
					
					reviewList.add(review);
				}
			}
			
			datosAuto.setAnio(origenCasted.getDatosAuto().getAnio());
			datosAuto.setColor(origenCasted.getDatosAuto().getColor());
			datosAuto.setMarca(origenCasted.getDatosAuto().getMarca());
			datosAuto.setModelo(origenCasted.getDatosAuto().getModelo());
			datosAuto.setMotor(origenCasted.getDatosAuto().getMotor());
			datosAuto.setPotencia(origenCasted.getDatosAuto().getPotencia());
			datosAuto.setOrdenamiento(origenCasted.getDatosAuto().getOrdenamiento());
			
			auto.setId(origenCasted.getId());
			auto.setAnioFabricacion(origenCasted.getAnioFabricacion());
			auto.setCantidadProducida(origenCasted.getCantidadProducida());
			auto.setDatosAuto(datosAuto);
			auto.setEdicionEspecial(origenCasted.getEdicionEspecial());
			auto.setFabricante(origenCasted.getFabricante());
			auto.setLoTengo(origenCasted.isLoTengo());
			auto.setReviews(reviewList);
			auto.setSerie(origenCasted.getSerie());
			auto.setUrlFotos(origenCasted.getUrlFotos());
			auto.setUrlRest(origenCasted.getUrlRest());
			
			respuesta = auto;
		} 
		else if (origen instanceof IdiomaRequest)
		{
			IdiomaRequest origenCasted = (IdiomaRequest) origen;
			Idioma idioma = new Idioma();
			idioma.setAbreviatura(origenCasted.getAbreviatura());
			idioma.setNombre(origenCasted.getNombre());

			respuesta = idioma;
		} 
		else if (origen instanceof TextosPaginaRequest)
		{
			TextosPaginaRequest origenCasted = (TextosPaginaRequest) origen;

			TextosPagina textosPagina = new TextosPagina();
			List<TextoPagina> textoPaginaList = new ArrayList<TextoPagina>();

			textosPagina.setIdioma(origenCasted.getIdioma());
			textosPagina.setPagina(origenCasted.getPagina());

			for (TextoPaginaRequest textoPaginaRequest : origenCasted.getTextosPagina())
			{
				TextoPagina textoPagina = new TextoPagina();
				textoPagina.setTag(textoPaginaRequest.getTag());
				textoPagina.setTexto(textoPaginaRequest.getTexto());

				textoPaginaList.add(textoPagina);
			}

			textosPagina.setTextosPagina(textoPaginaList);

			respuesta = textosPagina;
		}

		return respuesta;
	}

}
