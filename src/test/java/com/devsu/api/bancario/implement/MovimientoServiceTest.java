package com.devsu.api.bancario.implement;

import com.devsu.api.bancario.dto.MovimientoDTO;
import com.devsu.api.bancario.repository.MovimientoRepository;
import com.devsu.api.bancario.service.implement.CuentaService;
import com.devsu.api.bancario.service.implement.MovimientoService;
import com.devsu.api.bancario.dto.CuentaDTO;
import com.devsu.api.bancario.entity.CuentaEntity;
import com.devsu.api.bancario.entity.MovimientoEntity;
import com.devsu.api.bancario.enums.TypeMovimiento;
import com.devsu.api.bancario.Exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest
{

	@Mock
	private MovimientoRepository mockMovimientoRepository;
	@Mock
	private CuentaService mockCuentaService;

	@InjectMocks
	private MovimientoService movimientoServiceUnderTest;


	@Test
	void testFindById_MovimientoRepositoryReturnsAbsent()
	{
		when(mockMovimientoRepository.findById(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> movimientoServiceUnderTest.findById(0)).isInstanceOf(NotFoundException.class);
	}



	@Test
	void testCreate_CuentaServiceReturnsAbsent()
	{
		final MovimientoDTO movimiento = new MovimientoDTO();
		movimiento.setId(2);
		movimiento.setFecha(LocalDateTime.of(2022, 2, 5, 12, 3, 0));
		movimiento.setTipoMovimiento(TypeMovimiento.DEBITO);
		movimiento.setValor(0.0);
		movimiento.setSaldo(0.0);
		final CuentaDTO cuenta = new CuentaDTO();
		cuenta.setNumeroCuenta("498323872");
		movimiento.setCuenta(cuenta);

		when(mockCuentaService.findByNumeroCuenta("498323872")).thenReturn(Optional.empty());

		assertThatThrownBy(() -> movimientoServiceUnderTest.create(movimiento)).isInstanceOf(NotFoundException.class);
	}

	@Test
	void testGetUltimoMovimiento()
	{
		final MovimientoEntity movimiento = new MovimientoEntity();
		movimiento.setId(0);
		movimiento.setFecha(LocalDateTime.of(2022, 3, 4, 10, 0, 0));
		movimiento.setTipoMovimiento(TypeMovimiento.DEBITO);
		movimiento.setValor(0.0);
		movimiento.setSaldo(0.0);
		final CuentaEntity cuenta = new CuentaEntity();
		cuenta.setNumeroCuenta("98309490");
		cuenta.setSaldoInicial(0.0);
		cuenta.setMovimientos(List.of(new MovimientoEntity()));
		movimiento.setCuenta(cuenta);
		final List<MovimientoEntity> movimientos = List.of(movimiento);

		final double result = movimientoServiceUnderTest.getUltimoMovimiento(movimientos);

		assertThat(result).isEqualTo(0.0, within(0.0001));
	}



	@Test
	void testUpdate_MovimientoRepositoryFindByIdReturnsAbsent()
	{
		final MovimientoDTO movimiento = new MovimientoDTO();
		movimiento.setId(0);
		movimiento.setFecha(LocalDateTime.of(2022, 12, 12, 0, 0, 0));
		movimiento.setTipoMovimiento(TypeMovimiento.DEBITO);
		movimiento.setValor(0.0);
		movimiento.setSaldo(0.0);
		final CuentaDTO cuenta = new CuentaDTO();
		cuenta.setNumeroCuenta("22224444");
		movimiento.setCuenta(cuenta);

		when(mockMovimientoRepository.findById(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> movimientoServiceUnderTest.update(movimiento)).isInstanceOf(NotFoundException.class);
	}


	@Test
	void testDelete()
	{
		final MovimientoEntity movimiento1 = new MovimientoEntity();
		movimiento1.setId(0);
		movimiento1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
		movimiento1.setTipoMovimiento(TypeMovimiento.DEBITO);
		movimiento1.setValor(0.0);
		movimiento1.setSaldo(0.0);
		final CuentaEntity cuenta = new CuentaEntity();
		cuenta.setNumeroCuenta("99990000");
		cuenta.setSaldoInicial(0.0);
		cuenta.setMovimientos(List.of(new MovimientoEntity()));
		movimiento1.setCuenta(cuenta);
		final Optional<MovimientoEntity> movimiento = Optional.of(movimiento1);
		when(mockMovimientoRepository.findById(0)).thenReturn(movimiento);

		movimientoServiceUnderTest.delete(0);

		verify(mockMovimientoRepository).deleteById(0);
	}

	@Test
	void testDelete_MovimientoRepositoryFindByIdReturnsAbsent()
	{
		when(mockMovimientoRepository.findById(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> movimientoServiceUnderTest.delete(0)).isInstanceOf(NotFoundException.class);
	}
}
