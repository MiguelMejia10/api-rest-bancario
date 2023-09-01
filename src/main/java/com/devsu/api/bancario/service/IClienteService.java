package com.devsu.api.bancario.service;


import com.devsu.api.bancario.dto.ClienteDTO;
import com.devsu.api.bancario.entity.ClientEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IClienteService
{
	List<ClienteDTO> findAll();

	ClienteDTO findByClientId(final Integer clientId);

	ClienteDTO create(final ClienteDTO clienteDTO);

	ClienteDTO update(final ClienteDTO clienteDTO);

	ClienteDTO updatePatch(final Integer clienteId, final Map<String, Object> results);

	void delete(final Integer clienteId);

	Optional<ClientEntity> findByIdentificacion(final String identificacion);

	ClientEntity getMovByClienteId(final Integer clienteId, final LocalDate fechaInicial, final LocalDate fechaFinal);

}
