package ar.com.american118models.modelo.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.american118models.modelo.entidades.usuarios.ParDeClaves;

public interface ParDeClavesRepository extends MongoRepository<ParDeClaves, String>
{
}
