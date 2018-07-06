package ar.com.american118models.modelo.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.american118models.modelo.entidades.usuarios.Clave;

public interface ClaveRepository extends MongoRepository<Clave, String>
{
	public Clave findByPosicion(int posicion);
}
