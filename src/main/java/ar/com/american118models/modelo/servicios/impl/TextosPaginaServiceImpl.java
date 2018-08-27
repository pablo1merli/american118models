package ar.com.american118models.modelo.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.american118models.modelo.entidades.paginas.TextosPagina;
import ar.com.american118models.modelo.repositorios.TextosPaginaRepository;
import ar.com.american118models.modelo.servicios.TextosPaginaService;

@Service
public class TextosPaginaServiceImpl implements TextosPaginaService
{
	@Autowired
	TextosPaginaRepository textosPaginaRepository;
	
	public List<TextosPagina> getTextosPagina()
	{
		return textosPaginaRepository.findAll();
	}

	public TextosPagina getTextosPaginaPorIdioma(String pagina, String idioma)
	{
		return textosPaginaRepository.findByPaginaAndIdioma(pagina, idioma);
	}

	public boolean altaTextosPagina(TextosPagina textosPagina)
	{
		boolean respuesta = true;
		
		try
		{
			textosPaginaRepository.save(textosPagina);
		}
		catch (Exception e)
		{
			respuesta = false;
		}
		
		return respuesta;
	}
	
	public boolean bajaTextosPagina(TextosPagina textosPagina)
	{
		boolean respuesta = true;
		
		try
		{
			TextosPagina textosPaginaBD = textosPaginaRepository.findByPaginaAndIdioma(textosPagina.getPagina(), textosPagina.getIdioma());
			
			if (textosPaginaBD != null)
			{
				textosPaginaRepository.delete(textosPaginaBD);
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

	public TextosPagina modificarTextosPagina(TextosPagina textosPagina)
	{
		TextosPagina textosPaginaBD = textosPaginaRepository.findByPaginaAndIdioma(textosPagina.getPagina(), textosPagina.getIdioma());
		
		if (textosPaginaBD == null)
		{
			return null;
		}
		
		textosPaginaBD.setIdioma(textosPagina.getIdioma());
		textosPaginaBD.setPagina(textosPagina.getPagina());
		textosPaginaBD.setTextosPagina(textosPagina.getTextosPagina());
		
		textosPaginaBD = textosPaginaRepository.save(textosPaginaBD);
		
		return textosPaginaBD;
	}
}
