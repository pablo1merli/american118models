package ar.com.american118models.modelo.servicios.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.american118models.modelo.entidades.autos.Auto;
import ar.com.american118models.modelo.entidades.autos.Review;
import ar.com.american118models.modelo.repositorios.AutoRepository;
import ar.com.american118models.modelo.servicios.AutoService;

@Service
public class AutoServiceImpl implements AutoService
{
	@Autowired
	AutoRepository autoRepository;
	
	public Auto getAutoPorUrlRest(String urlRest)
	{
		Optional<Auto> autoBD = autoRepository.findByUrlRest(urlRest);
		
		return (autoBD.isPresent()) ? autoBD.get() : null;
	}
	
	public List<Auto> getAutos(boolean isAdministrador)
	{
		List<Auto> autoList = null;
		
		if (isAdministrador)
		{
			autoList = autoRepository.findAll();
		}
		else
		{
			autoList = autoRepository.findByReviewsNotNull();
		}
		
		return this.ordenamientoDefaultPorMarcaModeloAnioPotenciaYOrdenamiento(autoList);
	}
	
	public boolean altaAuto(Auto auto)
	{
		boolean respuesta = true;
		
		try
		{
			if (auto.getReviews() != null)
			{
				for (Review review : auto.getReviews())
				{
					if (review != null)
					{
						review.setFecha(new LocalDate());
					}
				}
			}
			
			autoRepository.save(auto);
		}
		catch (Exception e)
		{
			respuesta = false;
		}
		
		return respuesta;
	}

	public boolean bajaAuto(Auto auto)
	{
		boolean respuesta = true;
		
		try
		{
			Optional<Auto> autoBD = autoRepository.findById(auto.getId());
			
			if (autoBD.get() != null)
			{
				autoRepository.delete(autoBD.get());
			}
			else
			{
				respuesta = false;
			}
		}
		catch (Exception e)
		{
			respuesta = false;
		}
		
		return respuesta;
	}

	public Auto modificarAuto(Auto auto)
	{
		Optional<Auto> optionalAutoBD = autoRepository.findById(auto.getId());
		
		if (optionalAutoBD.get() == null)
		{
			return null;
		}
		
		Auto autoBD = optionalAutoBD.get();
		
		if (auto.getReviews() != null)
		{
			for (Review review : auto.getReviews())
			{
				if (review.getFecha() == null)
				{
					review.setFecha(new LocalDate());
				}
			}
		}
		
		autoBD.setAnioFabricacion(auto.getAnioFabricacion());
		autoBD.setCantidadProducida(auto.getCantidadProducida());
		autoBD.setDatosAuto(auto.getDatosAuto());
		autoBD.setEdicionEspecial(auto.getEdicionEspecial());
		autoBD.setFabricante(auto.getFabricante());
		autoBD.setLoTengo(auto.isLoTengo());
		autoBD.setReviews(auto.getReviews());
		autoBD.setSerie(auto.getSerie());
		autoBD.setUrlFotos(auto.getUrlFotos());
		autoBD.setUrlRest(auto.getUrlRest());
		
		autoBD = autoRepository.save(autoBD);
		
		return autoBD;
	}

	private List<Auto> ordenamientoDefaultPorMarcaModeloAnioPotenciaYOrdenamiento(List<Auto> autoList)
	{
		autoList.sort(Comparator.comparingInt((Auto auto) -> auto.getDatosAuto().getOrdenamiento())
								.thenComparingInt(auto -> auto.getDatosAuto().getAnio())
								.thenComparing(auto -> auto.getDatosAuto().getMarca())
								.thenComparing(auto -> auto.getDatosAuto().getModelo())
								.thenComparingInt(auto -> auto.getDatosAuto().getPotencia())
						);
		
		return autoList;
	}
}
