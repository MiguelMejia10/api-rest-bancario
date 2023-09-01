package com.devsu.api.bancario.repository;

import com.devsu.api.bancario.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Integer>
{
}
