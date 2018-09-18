package ar.com.american118models.rest;

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

import ar.com.american118models.modelo.entidades.usuarios.Administrador;
import ar.com.american118models.modelo.servicios.AdministradorService;
import ar.com.american118models.rest.request.CredencialesUsuarioRequest;
import ar.com.american118models.rest.response.ErrorResponse;
import ar.com.american118models.rest.response.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@Path("rest/administrador/")
@Api(value = "american118models-api")
public class AdministradorRestEndpoint 
{
	@Autowired
	AdministradorService administradorService;

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
		respuestaJson.put("Echo Administrador - Mensaje Recibido: ", mensaje);
		return Response.status(Response.Status.OK).entity(respuestaJson.toString()).build();
	}

	@POST
	@Path("/existeAdministrador")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verifica si existe el usuario administrador del sitio", notes = "Verifica si existe el usuario administrador del sitio.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response existeAdministrador() throws Exception
	{
		boolean response;

		try
		{
			response = administradorService.isExisteAdministrador();
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
	@Path("/loginAdministrador")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Autentificacion del usuario administrador en el sitio", notes = "Autentificacion del usuario administrador en el sitio.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response loginAdministrador(CredencialesUsuarioRequest request) throws Exception
	{
		TokenResponse response = new TokenResponse();

		try
		{
			response.setToken(administradorService.loginAdministrador(request.getUsuario(), request.getPassword()));
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
	@Path("/logoutAdministrador")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cambia el estado del administrador en la BD como no autenticado", notes = "Cambia el estado del administrador en la BD como no autenticado.")
	@ApiResponses(value =
	{ @ApiResponse(code = 200, message = "OK", response = Object.class),
			@ApiResponse(code = 500, message = "Error", response = ErrorResponse.class) })
	public Response logoutAdministrador(String token) throws Exception
	{
		boolean response = false;

		try
		{
			Administrador administrador = administradorService.cambiarEstado(token, null, false);
			
			// Si se cambia el estado correctamente, ya no se encuentra autenticado
			if (!administrador.isEstaAutenticado())
			{
				response = true;
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
