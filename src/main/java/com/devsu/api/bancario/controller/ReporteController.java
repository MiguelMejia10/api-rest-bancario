package com.devsu.api.bancario.controller;

import com.devsu.api.bancario.dto.report.ReporteDTO;
import com.devsu.api.bancario.service.IReporteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/reportes")
public class ReporteController
{
	@Autowired
	private IReporteService reporteService;

	@GetMapping
	public ResponseEntity<ReporteDTO> getReporte(@Valid @RequestParam("clienteId") Integer clienteId,
												 @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
												 @RequestParam("fechaFinal") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFinal)
	{
		return ResponseEntity.status(HttpStatus.OK).body(reporteService.getReporte(clienteId, fechaInicio, fechaFinal));
	}
}
