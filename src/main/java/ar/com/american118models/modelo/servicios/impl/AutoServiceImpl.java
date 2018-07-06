package ar.com.american118models.modelo.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.american118models.modelo.entidades.autos.Auto;
import ar.com.american118models.modelo.repositorios.AutoRepository;
import ar.com.american118models.modelo.servicios.AutoService;

@Service
public class AutoServiceImpl implements AutoService
{
	@Autowired
	AutoRepository autoRepository;
	
	public Auto buscarAutoPorUrlRest(String urlRest)
	{
		return autoRepository.findByUrlRest(urlRest);
	}
}
