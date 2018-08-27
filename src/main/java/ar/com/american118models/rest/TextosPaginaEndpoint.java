package ar.com.american118models.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ar.com.american118models.mapping.ObjectMapping;
import ar.com.american118models.modelo.entidades.paginas.TextosPagina;
import ar.com.american118models.modelo.servicios.AdministradorService;
import ar.com.american118models.modelo.servicios.TextosPaginaService;
import ar.com.american118models.rest.request.TextosPaginaRequest;
import ar.com.american118models.rest.response.ErrorResponse;
import ar.com.american118models.rest.response.TextosPaginaResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Path("rest/textosPagina/")
@Api(value = "american118models-api")
public class TextosPaginaEndpoint 
{
	@Autowired
	TextosPaginaService textosPaginaService;
	@Autowired
	AdministradorService administradorService;
	@Autowired
	ObjectMapping mapping;

	@SuppressWarnings("unchecked")
	@POST
	@Path("/echo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Prueba echo del Endpoint", notes = "Prueba echo del Endpoint.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK.") })
	public Response echo(
			@ApiParam(name = "mensaje", value = "Mensaje a retornar por el servicio.", required = true)String mensaje)
	{
		JSONObject respuestaJson = new JSONObject();
		respuestaJson.put("Echo TextosPagina - Mensaje Recibido: ", mensaje);
		return Response.status(Response.Status.OK).entity(respuestaJson.toString()).build();
	}

	@POST
	@Path("/getTextosPagina")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene todos los textos de una pagina de la BD", notes = "Obtiene todos los textos de una pagina de la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response getTextosPagina(TextosPaginaRequest textosPaginaRequest) throws Exception
	{
		List<TextosPaginaResponse> response;

		try
		{
			response = new ArrayList<TextosPaginaResponse>();

			// Obtengo todos los textos
			List<TextosPagina> listaTextosPagina = textosPaginaService.getTextosPagina();
			for (TextosPagina textosPagina : listaTextosPagina)
			{
				TextosPaginaResponse textosPaginaResponse = (TextosPaginaResponse) mapping.convertir(textosPagina, TextosPaginaResponse.class);
				
				response.add(textosPaginaResponse);
			}
			
		}
		catch (Exception ex)
		{
			ErrorResponse error = new ErrorResponse();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(error).type("application/json")
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).entity(response).type("application/json").build();
	}

	@POST
	@Path("/getTextosDePaginaSegunIdioma")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene todos los textos de una pagina de la BD, segun el idioma", notes = "Obtiene todos los textos de una pagina de la BD, segun el idioma.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response getTextosDePaginaSegunIdioma(TextosPaginaRequest textosPaginaRequest) throws Exception
	{
		TextosPaginaResponse response;

		try
		{
			response = new TextosPaginaResponse();

			// Obtengo los textos de la pagina pasada como parametro, correspondientes al idioma seleccionado
			TextosPagina textosPagina = textosPaginaService.getTextosPaginaPorIdioma(textosPaginaRequest.getPagina(), textosPaginaRequest.getIdioma());
			
			response = (TextosPaginaResponse) mapping.convertir(textosPagina, TextosPaginaResponse.class);
		}
		catch (Exception ex)
		{
			ErrorResponse error = new ErrorResponse();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(error).type("application/json")
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).entity(response).type("application/json").build();
	}

	@POST
	@Path("/altaTextosPagina")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Alta de los textos de las paginas", notes = "Alta de los textos de las paginas.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response altaTextosPagina(TextosPaginaRequest textosPaginaRequest) throws Exception
	{
		boolean response = false;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(textosPaginaRequest.getToken()))
			{
				TextosPagina textosPagina = (TextosPagina) mapping.convertir(textosPaginaRequest, TextosPagina.class);
				
				response = textosPaginaService.altaTextosPagina(textosPagina);
			}
		}
		catch (Exception ex)
		{
			ErrorResponse error = new ErrorResponse();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(error).type("application/json")
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).entity(response).type("application/json").build();
	}

	@POST
	@Path("/bajaTextosPagina")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Da de baja los textos de una pagina en la BD", notes = "Da de baja los textos de una pagina en la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response bajaTextosPagina(TextosPaginaRequest textosPaginaRequest) throws Exception
	{
		boolean response = false;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(textosPaginaRequest.getToken()))
			{
				TextosPagina textosPagina = (TextosPagina) mapping.convertir(textosPaginaRequest, TextosPagina.class);
				
				response = textosPaginaService.bajaTextosPagina(textosPagina);
			}
		}
		catch (Exception ex)
		{
			ErrorResponse error = new ErrorResponse();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(error).type("application/json")
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).entity(response).type("application/json").build();
	}

	@POST
	@Path("/modificarTextosPagina")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica los textos de una pagina en la BD", notes = "Modifica los textos de una pagina en la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response modificarTextosPagina(TextosPaginaRequest textosPaginaRequest) throws Exception
	{
		TextosPagina response = null;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(textosPaginaRequest.getToken()))
			{
				TextosPagina textosPagina = (TextosPagina) mapping.convertir(textosPaginaRequest, TextosPagina.class);
				
				response = textosPaginaService.modificarTextosPagina(textosPagina);
			}
		}
		catch (Exception ex)
		{
			ErrorResponse error = new ErrorResponse();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(error).type("application/json")
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).entity(response).type("application/json").build();
	}
}