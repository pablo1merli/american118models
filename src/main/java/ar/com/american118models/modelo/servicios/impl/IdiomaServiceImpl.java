package ar.com.american118models.modelo.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.american118models.modelo.entidades.idiomas.Idioma;
import ar.com.american118models.modelo.repositorios.IdiomaRepository;
import ar.com.american118models.modelo.servicios.IdiomaService;

@Service
public class IdiomaServiceImpl implements IdiomaService
{
	@Autowired
	IdiomaRepository idiomaRepository;
	
	public List<Idioma> getIdiomas()
	{
		List<Idioma> listaIdiomas= idiomaRepository.findAll();
		
		return listaIdiomas;
	}
	
	public boolean altaIdioma(Idioma idioma)
	{
		boolean respuesta = true;
		
		try
		{
			idiomaRepository.save(idioma);
		}
		catch (Exception e)
		{
			respuesta = false;
		}
		
		return respuesta;
	}
	
	public boolean bajaIdioma(Idioma idioma)
	{
		boolean respuesta = true;
		
		try
		{
			Idioma idiomaBD = idiomaRepository.findByAbreviatura(idioma.getAbreviatura());
			
			if (idiomaBD != null)
			{
				idiomaRepository.delete(idiomaBD);
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

	public Idioma modificarIdioma(Idioma idioma)
	{
		Idioma idiomaBD = idiomaRepository.findByAbreviatura(idioma.getAbreviatura());
		
		if (idiomaBD == null)
		{
			return null;
		}
		
		idiomaBD.setAbreviatura(idioma.getAbreviatura());
		idiomaBD.setNombre(idioma.getNombre());
		
		idiomaBD = idiomaRepository.save(idiomaBD);
		
		return idiomaBD;
	}
}
