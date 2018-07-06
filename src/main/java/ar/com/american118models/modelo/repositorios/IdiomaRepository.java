package ar.com.american118models.modelo.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.american118models.modelo.entidades.idiomas.Idioma;;

public interface IdiomaRepository extends MongoRepository<Idioma, String>
{
	public Idioma findByAbreviatura(String abreviatura);
}
