package com.devsu.api.bancario.mapper;


import com.devsu.api.bancario.dto.MovimientoDTO;
import com.devsu.api.bancario.entity.MovimientoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovimientoMapper
{
	MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);

	MovimientoDTO movimientoToMoviemientoDTO(final MovimientoEntity movimiento);
	MovimientoEntity movimientoDTOToMovimiento(final MovimientoDTO movimientoDTO);

}
