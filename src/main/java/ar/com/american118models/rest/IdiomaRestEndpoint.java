package ar.com.american118models.rest;

import java.util.ArrayList;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.CrossOrigin;

import ar.com.american118models.mapping.ObjectMapping;
import ar.com.american118models.modelo.entidades.idiomas.Idioma;
import ar.com.american118models.modelo.servicios.AdministradorService;
import ar.com.american118models.modelo.servicios.IdiomaService;
import ar.com.american118models.rest.request.IdiomaRequest;
import ar.com.american118models.rest.response.ErrorResponse;
import ar.com.american118models.rest.response.IdiomaResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@Path("rest/idioma/")
@Api(value = "american118models-api")
public class IdiomaRestEndpoint 
{
	@Autowired
	IdiomaService idiomaService;
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
		respuestaJson.put("Echo Idioma - Mensaje Recibido: ", mensaje);
		return Response.status(Response.Status.OK).entity(respuestaJson.toString()).build();
	}

	@POST
	@Path("/getIdiomas")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene todos los idiomas de la BD", notes = "Obtiene todos los idiomas de la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response getIdiomas() throws Exception
	{
		List<IdiomaResponse> response;

		try
		{
			response = new ArrayList<IdiomaResponse>();

			// Obtengo el idioma seleccionado desde la BD y lo cargo en el response
			List<Idioma> idiomas = idiomaService.getIdiomas();
			for (Idioma idioma : idiomas)
			{
				IdiomaResponse idiomaResponse = (IdiomaResponse) mapping.convertir(idioma, IdiomaResponse.class);
				response.add(idiomaResponse);
			}
			
		}
		catch (Exception ex)
		{
			ErrorResponse error = new ErrorResponse();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(error).type("application/json")
					.build();
		}

		response.sort(Comparator.comparing(IdiomaResponse::getNombre));
		
		return Response.status(Response.Status.OK.getStatusCode()).entity(response).type("application/json").build();
	}

	@POST
	@Path("/altaIdioma")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Da de alta el Idioma en la BD", notes = "Da de alta el Idioma en la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response altaIdioma(IdiomaRequest idiomaRequest) throws Exception
	{
		boolean response = false;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(idiomaRequest.getToken()))
			{
				Idioma idioma = (Idioma) mapping.convertir(idiomaRequest, Idioma.class);
				
				response = idiomaService.altaIdioma(idioma);
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
	@Path("/bajaIdioma")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Da de baja el Idioma en la BD", notes = "Da de baja el Idioma en la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response bajaIdioma(IdiomaRequest idiomaRequest) throws Exception
	{
		boolean response = false;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(idiomaRequest.getToken()))
			{
				Idioma idioma = (Idioma) mapping.convertir(idiomaRequest, Idioma.class);
				
				response = idiomaService.bajaIdioma(idioma);
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
	@Path("/modificarIdioma")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un Idioma en la BD", notes = "Modifica un Idioma en la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response modificarIdioma(IdiomaRequest idiomaRequest) throws Exception
	{
		Idioma response = null;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(idiomaRequest.getToken()))
			{
				Idioma idioma = (Idioma) mapping.convertir(idiomaRequest, Idioma.class);
				
				response = idiomaService.modificarIdioma(idioma);
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
