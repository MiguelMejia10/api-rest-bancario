package com.devsu.api.bancario.service;

import com.devsu.api.bancario.dto.report.ReporteDTO;

import java.time.LocalDate;

public interface IReporteService
{
	ReporteDTO getReporte(Integer clienteId, LocalDate fechaInicial, LocalDate fechafinal);

}
