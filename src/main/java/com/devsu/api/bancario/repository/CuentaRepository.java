package com.devsu.api.bancario.repository;

import com.devsu.api.bancario.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer>
{
	Optional<CuentaEntity> findByNumeroCuenta(final String numeroCuenta);

}
