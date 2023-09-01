package com.devsu.api.bancario.service.implement;

import com.devsu.api.bancario.Exceptions.ExceptionsConstants;
import com.devsu.api.bancario.Exceptions.NotFoundException;
import com.devsu.api.bancario.dto.report.CuentaImprDTO;
import com.devsu.api.bancario.dto.report.MovimientoImprDTO;
import com.devsu.api.bancario.dto.report.ReporteDTO;
import com.devsu.api.bancario.entity.ClientEntity;
import com.devsu.api.bancario.entity.MovimientoEntity;
import com.devsu.api.bancario.service.IReporteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class ReporteService implements IReporteService
{
	@Autowired
	private ClienteService clienteService;

	@Override
	public ReporteDTO getReporte(Integer clienteId, LocalDate fechaInicial, LocalDate fechafinal)
	{
		final ClientEntity cliente = clienteService.getMovByClienteId(clienteId, fechaInicial, fechafinal);

		if (Objects.isNull(cliente))
		{
			log.error(ExceptionsConstants.CLIENTE_NO_EXISTE, clienteId);
			throw new NotFoundException(String.format(ExceptionsConstants.CLIENTE_NO_EXISTE, clienteId));
		}

		ReporteDTO reporteDTO = new ReporteDTO();
		reporteDTO.setClienteId(cliente.getClientId());
		reporteDTO.setNombre(cliente.getNombre());
		reporteDTO.setIdentificacion(cliente.getIdentificacion());

		List<CuentaImprDTO> cuentaReporteDTOList = cliente.getCuentas().stream().map(cuenta -> {
			CuentaImprDTO cuentaReporteDTO = new CuentaImprDTO();
			cuentaReporteDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
			cuentaReporteDTO.setTipoCuenta(cuenta.getTipoCuenta().toString());
			cuentaReporteDTO.setSaldo(cuenta.getSaldoInicial());

			List<MovimientoImprDTO> movimientosReporteDTO = cuenta.getMovimientos().stream().map(m -> {
				MovimientoImprDTO movimientoReporteDTO = new MovimientoImprDTO();
				movimientoReporteDTO.setFecha(m.getFecha());
				movimientoReporteDTO.setTipoMovimiento(m.getTipoMovimiento());
				movimientoReporteDTO.setValor(m.getValor());
				movimientoReporteDTO.setSaldo(m.getSaldo());
				return movimientoReporteDTO;
			}).collect(Collectors.toList());

			double totalDebitos = cuenta.getMovimientos().stream()
					.filter(m -> ExceptionsConstants.DEBITO.equals(m.getTipoMovimiento().getValue())).mapToDouble(MovimientoEntity::getValor)
					.sum();

			double totalCreditos = cuenta.getMovimientos().stream()
					.filter(m -> ExceptionsConstants.CREDITO.equals(m.getTipoMovimiento().getValue())).mapToDouble(MovimientoEntity::getValor)
					.sum();

			cuentaReporteDTO.setMovimientos(movimientosReporteDTO);
			cuentaReporteDTO.setTotalCreditos(totalCreditos);
			cuentaReporteDTO.setTotalDebitos(totalDebitos);
			cuentaReporteDTO.setSaldo(cuentaReporteDTO.getSaldo() + (totalCreditos - totalDebitos));
			return cuentaReporteDTO;
		}).collect(Collectors.toList());

		reporteDTO.setCuentas(cuentaReporteDTOList);
		reporteDTO.setSaldoTotalGeneral(cuentaReporteDTOList.stream().mapToDouble(CuentaImprDTO::getSaldo).sum());
		reporteDTO.setCreditoTotalGeneral(cuentaReporteDTOList.stream().mapToDouble(CuentaImprDTO::getTotalCreditos).sum());
		reporteDTO.setDebitoTotalGeneral(cuentaReporteDTOList.stream().mapToDouble(CuentaImprDTO::getTotalDebitos).sum());

		log.info(ExceptionsConstants.REPORTE_GENERADO);
		return reporteDTO;
	}
}
