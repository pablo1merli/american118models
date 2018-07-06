package ar.com.american118models.modelo.servicios;

import ar.com.american118models.modelo.entidades.usuarios.Administrador;

public interface AdministradorService
{
	public abstract boolean isExisteAdministrador();
	public abstract String loginAdministrador(String usuario, String password);
	public abstract Administrador cambiarEstado(String token, String usuario, boolean estaAutenticado);
	public abstract boolean controlarAdministradorAutenticadoDesdeToken(String token);
}
