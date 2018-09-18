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
import org.springframework.web.bind.annotation.CrossOrigin;

import ar.com.american118models.mapping.ObjectMapping;
import ar.com.american118models.modelo.entidades.autos.Auto;
import ar.com.american118models.modelo.servicios.AdministradorService;
import ar.com.american118models.modelo.servicios.AutoService;
import ar.com.american118models.rest.request.AutoRequest;
import ar.com.american118models.rest.response.AutoResponse;
import ar.com.american118models.rest.response.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@Path("rest/auto/")
@Api(value = "american118models-api")
public class AutoEndpoint 
{
	@Autowired
	AutoService autoService;
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
		respuestaJson.put("Echo Auto - Mensaje Recibido: ", mensaje);
		return Response.status(Response.Status.OK).entity(respuestaJson.toString()).build();
	}

	@POST
	@Path("/getAutoPorUrlRest")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene el auto de la BD, segun urlRest", notes = "Obtiene el auto de la BD, segun urlRest.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response getAutoPorUrlRest(String urlRest) throws Exception
	{
		AutoResponse response = null;

		try
		{
			Auto auto = autoService.getAutoPorUrlRest(urlRest);
			
			if (auto != null)
			{
				response = (AutoResponse) mapping.convertir(auto, AutoResponse.class);
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
	@Path("/getAutos")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene todos los autos de la BD", notes = "Obtiene todos los autos de la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response getAutos(AutoRequest autoRequest) throws Exception
	{
		List<AutoResponse> response;

		try
		{
			response = new ArrayList<AutoResponse>();
			boolean isAdministrador = false;
			
			if ((autoRequest.getToken() != null) && administradorService.controlarAdministradorAutenticadoDesdeToken(autoRequest.getToken()))
			{
				isAdministrador = true;
			}
			
			// Obtengo todos los autos
			List<Auto> listaAutos = autoService.getAutos(isAdministrador);
			for (Auto auto : listaAutos)
			{
				AutoResponse autoResponse = (AutoResponse) mapping.convertir(auto, AutoResponse.class);
				
				response.add(autoResponse);
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
	@Path("/altaAuto")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Alta de un auto", notes = "Alta de un auto.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response altaAuto(AutoRequest autoRequest) throws Exception
	{
		boolean response = false;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(autoRequest.getToken()))
			{
				Auto auto = (Auto) mapping.convertir(autoRequest, Auto.class);
				
				response = autoService.altaAuto(auto);
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
	@Path("/bajaAuto")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Da de baja un auto en la BD", notes = "Da de baja un auto en la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response bajaAuto(AutoRequest autoRequest) throws Exception
	{
		boolean response = false;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(autoRequest.getToken()))
			{
				Auto auto = (Auto) mapping.convertir(autoRequest, Auto.class);
				
				response = autoService.bajaAuto(auto);
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
	@Path("/modificarAuto")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un auto en la BD", notes = "Modifica un auto en la BD.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response modificarAuto(AutoRequest autoRequest) throws Exception
	{
		Auto response = null;

		try
		{
			// Controlo que sea el administrador autenticado
			if (administradorService.controlarAdministradorAutenticadoDesdeToken(autoRequest.getToken()))
			{
				Auto auto = (Auto) mapping.convertir(autoRequest, Auto.class);
				
				response = autoService.modificarAuto(auto);
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