package com.devsu.api.bancario.service;

import com.devsu.api.bancario.dto.CuentaDTO;
import com.devsu.api.bancario.entity.CuentaEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICuentaService
{
	List<CuentaDTO> findAll();

	CuentaDTO findById(final Integer id);

	CuentaDTO create(final CuentaDTO cuentaDTO);

	CuentaDTO update(final CuentaDTO cuentaDTO);

	CuentaDTO updatePatch(final Integer id, final Map<String, Object> results);

	void delete(final Integer id);

	Optional<CuentaEntity> findByNumeroCuenta(final String numeroCuenta);

}
