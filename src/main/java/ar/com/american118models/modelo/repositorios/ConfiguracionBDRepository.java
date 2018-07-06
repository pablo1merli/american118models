package ar.com.american118models.modelo.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.american118models.modelo.entidades.usuarios.ConfiguracionBD;

public interface ConfiguracionBDRepository extends MongoRepository<ConfiguracionBD, String>
{
	public ConfiguracionBD findByClave(String clave);
}
