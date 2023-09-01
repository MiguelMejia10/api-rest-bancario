package com.devsu.api.bancario.mapper;

import com.devsu.api.bancario.dto.ClienteDTO;
import com.devsu.api.bancario.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper
{

	ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

	ClienteDTO clienteToClienteDTO(final ClientEntity cliente);
	ClientEntity clienteDTOToCliente(final ClienteDTO clienteDTO);

}
