package ar.com.american118models.modelo.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.american118models.modelo.entidades.autos.Auto;

public interface AutoRepository extends MongoRepository<Auto, String>
{
	public Auto findByUrlRest(String urlRest);
}
