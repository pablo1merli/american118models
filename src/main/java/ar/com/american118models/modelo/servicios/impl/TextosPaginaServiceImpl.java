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
}
