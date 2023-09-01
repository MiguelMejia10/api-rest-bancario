package com.devsu.api.bancario.repository;

import com.devsu.api.bancario.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClientEntity, Integer>
{
	Optional<ClientEntity> findByIdentificacion(final String identificacion);
	Optional<ClientEntity> findByClientId(final Integer clienteId);
	void deleteByClientId(final Integer clientId);
}
