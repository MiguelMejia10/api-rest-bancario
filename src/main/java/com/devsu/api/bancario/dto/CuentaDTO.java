package com.devsu.api.bancario.dto;

import com.devsu.api.bancario.enums.TypeCuenta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaDTO
{
	private Integer id;

	@NotEmpty(message = "Numero Cuenta es un campo obligatorio")
	@Pattern(regexp = "^[0-9]{6,11}$", message = "Verificar el número de la cuenta")
	@Column(name = "numero_cuenta", unique = true)
	private String numeroCuenta;

	@NotNull(message = "Tipo Cuenta es un campo obligatorio y solo puede ser AHORROS O CORRIENTE")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_cuenta")
	private TypeCuenta tipoCuenta;

	@NotNull(message = "Saldo Inicial es un campo obligatorio")
	@Column(name = "saldo_inicial")
	private Double saldoInicial;

	@NotNull(message = "Estado es un campo obligatorio y solo puede ser true o false")
	private Boolean estado;

	private ClienteDTO cliente;
}

