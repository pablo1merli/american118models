package ar.com.american118models.modelo.servicios;

import java.util.List;

import ar.com.american118models.modelo.entidades.autos.Auto;

public interface AutoService
{
	public abstract Auto getAutoPorUrlRest(String urlRest);
	public abstract List<Auto> getAutos(boolean isAdministrador);
//	public abstract List<Auto> getAutosPorCriterioDeBusqueda(String criterioDeBusqueda);
	public abstract boolean altaAuto(Auto auto);
	public abstract boolean bajaAuto(Auto auto);
	public abstract Auto modificarAuto(Auto auto);
}
