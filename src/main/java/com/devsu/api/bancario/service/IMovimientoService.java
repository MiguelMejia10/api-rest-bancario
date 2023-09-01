package com.devsu.api.bancario.service;

import com.devsu.api.bancario.dto.MovimientoDTO;

import java.util.List;

public interface IMovimientoService
{
	List<MovimientoDTO> findAll();

	MovimientoDTO findById(final Integer id);

	MovimientoDTO create(final MovimientoDTO movimientoDTO);

	MovimientoDTO update(final MovimientoDTO movimientoDTO);

	void delete(final Integer id);
}
