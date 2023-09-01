package com.devsu.api.bancario.dto.report;


import com.devsu.api.bancario.enums.TypeMovimiento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientoImprDTO
{
	private TypeMovimiento tipoMovimiento;
	private LocalDateTime fecha;
	private Double valor;
	private Double saldo;
}
