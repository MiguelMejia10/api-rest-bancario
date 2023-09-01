package com.devsu.api.bancario.mapper;


import com.devsu.api.bancario.dto.CuentaDTO;
import com.devsu.api.bancario.entity.CuentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CuentaMapper
{
	CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

	CuentaDTO cuentaToCuentaDTO(final CuentaEntity cuenta);
	CuentaEntity cuentaDTOToCuenta(final CuentaDTO cuentaDTO);

}
