package ar.com.american118models.modelo.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.american118models.modelo.entidades.usuarios.Administrador;

public interface AdministradorRepository extends MongoRepository<Administrador, String>
{
	public Administrador findByUsuario(String usuario);
}
