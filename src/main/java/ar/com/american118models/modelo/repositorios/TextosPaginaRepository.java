package ar.com.american118models.modelo.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ar.com.american118models.modelo.entidades.paginas.TextosPagina;;

public interface TextosPaginaRepository extends MongoRepository<TextosPagina, String>
{
	@Query("{ 'pagina' : ?0, 'idioma' : ?1 }")
	public TextosPagina findByPaginaAndIdioma(String pagina, String idioma);
}
