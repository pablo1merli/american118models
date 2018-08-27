package ar.com.american118models.modelo.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ar.com.american118models.modelo.entidades.autos.Auto;

public interface AutoRepository extends MongoRepository<Auto, String>
{
	public Optional<Auto> findById(String id);
	public Optional<Auto> findByUrlRest(String urlRest);
	public List<Auto> findByReviewsNotNull();
}
