package ar.com.american118models.mapping.converters.desdeModeloAResponse;

import java.util.ArrayList;
import java.util.List;

import org.dozer.CustomConverter;

import ar.com.american118models.modelo.entidades.autos.Auto;
import ar.com.american118models.modelo.entidades.autos.Review;
import ar.com.american118models.modelo.entidades.idiomas.Idioma;
import ar.com.american118models.modelo.entidades.paginas.TextoPagina;
import ar.com.american118models.modelo.entidades.paginas.TextosPagina;
import ar.com.american118models.rest.response.AutoResponse;
import ar.com.american118models.rest.response.DatosAutoResponse;
import ar.com.american118models.rest.response.IdiomaResponse;
import ar.com.american118models.rest.response.ReviewResponse;
import ar.com.american118models.rest.response.TextoPaginaResponse;
import ar.com.american118models.rest.response.TextosPaginaResponse;

public class DesdeModeloAResponseConverter implements CustomConverter
{

	@Override
	public Object convert(Object destino, Object origen, Class<?> claseDestino, Class<?> claseOrigen)
	{
		Object respuesta = null;

		if (origen == null)
		{
			return null;
		}

		if (origen instanceof Auto)
		{
			Auto origenCasted = (Auto) origen;
			AutoResponse autoResponse = new AutoResponse();
			List<ReviewResponse> reviewListResponse = new ArrayList<ReviewResponse>();
			DatosAutoResponse datosAutoResponse = new DatosAutoResponse();
			
			if (origenCasted.getReviews() != null)
			{
				for (Review review: origenCasted.getReviews())
				{
					ReviewResponse reviewResponse = new ReviewResponse();
					
					reviewResponse.setIdioma(review.getIdioma());
					reviewResponse.setReview(review.getReview());
					reviewResponse.setFecha(review.getFecha());
					
					reviewListResponse.add(reviewResponse);
				}
			}
			
			datosAutoResponse.setAnio(origenCasted.getDatosAuto().getAnio());
			datosAutoResponse.setColor(origenCasted.getDatosAuto().getColor());
			datosAutoResponse.setMarca(origenCasted.getDatosAuto().getMarca());
			datosAutoResponse.setModelo(origenCasted.getDatosAuto().getModelo());
			datosAutoResponse.setMotor(origenCasted.getDatosAuto().getMotor());
			datosAutoResponse.setPotencia(origenCasted.getDatosAuto().getPotencia());
			datosAutoResponse.setOrdenamiento(origenCasted.getDatosAuto().getOrdenamiento());
			
			autoResponse.setId(origenCasted.getId());
			autoResponse.setAnioFabricacion(origenCasted.getAnioFabricacion());
			autoResponse.setCantidadProducida(origenCasted.getCantidadProducida());
			autoResponse.setDatosAuto(datosAutoResponse);
			autoResponse.setEdicionEspecial(origenCasted.getEdicionEspecial());
			autoResponse.setFabricante(origenCasted.getFabricante());
			autoResponse.setLoTengo(origenCasted.isLoTengo());
			autoResponse.setReviews(reviewListResponse);
			autoResponse.setSerie(origenCasted.getSerie());
			autoResponse.setUrlFotos(origenCasted.getUrlFotos());
			autoResponse.setUrlRest(origenCasted.getUrlRest());
			
			respuesta = autoResponse;
		} 
		else if (origen instanceof Idioma)
		{
			Idioma origenCasted = (Idioma) origen;
			IdiomaResponse idiomaResponse = new IdiomaResponse();
			idiomaResponse.setAbreviatura(origenCasted.getAbreviatura());
			idiomaResponse.setNombre(origenCasted.getNombre());

			respuesta = idiomaResponse;
		} 
		else if (origen instanceof TextosPagina)
		{
			TextosPagina origenCasted = (TextosPagina) origen;

			TextosPaginaResponse textosPaginaResponse = new TextosPaginaResponse();
			List<TextoPaginaResponse> textoPaginaList = new ArrayList<TextoPaginaResponse>();

			textosPaginaResponse.setIdioma(origenCasted.getIdioma());
			textosPaginaResponse.setPagina(origenCasted.getPagina());

			for (TextoPagina textoPagina : origenCasted.getTextosPagina())
			{
				TextoPaginaResponse textoPaginaResponse = new TextoPaginaResponse();
				textoPaginaResponse.setTag(textoPagina.getTag());
				textoPaginaResponse.setTexto(textoPagina.getTexto());
				textoPaginaList.add(textoPaginaResponse);
			}

			textosPaginaResponse.setTextosPagina(textoPaginaList);

			respuesta = textosPaginaResponse;
		}

		return respuesta;
	}

}
