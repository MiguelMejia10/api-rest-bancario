package com.devsu.api.bancario.dto;

import com.devsu.api.bancario.enums.TypeMovimiento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientoDTO
{
	private Integer id;
	private LocalDateTime fecha;

	@NotNull(message = "Tipo movimiento es un campo obligatorio y solo puede ser AHORROS O CORRIENTE")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_movimiento")
	private TypeMovimiento tipoMovimiento;

	@NotNull(message = "Valor es un campo obligatorio")
	private Double valor;

	private Double saldo;

	private CuentaDTO cuenta;
}
