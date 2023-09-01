package com.devsu.api.bancario.implement;

import com.devsu.api.bancario.Exceptions.NotFoundException;
import com.devsu.api.bancario.dto.CuentaDTO;
import com.devsu.api.bancario.entity.ClientEntity;
import com.devsu.api.bancario.entity.CuentaEntity;
import com.devsu.api.bancario.enums.TypeCuenta;
import com.devsu.api.bancario.repository.CuentaRepository;
import com.devsu.api.bancario.service.implement.ClienteService;
import com.devsu.api.bancario.service.implement.CuentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest
{

	@Mock
	private CuentaRepository mockCuentaRepository;
	@Mock
	private ClienteService mockClienteService;

	@InjectMocks
	private CuentaService cuentaServiceUnderTest;


	@Test
	void testFindById_CuentaRepositoryReturnsAbsent()
	{
		when(mockCuentaRepository.findById(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> cuentaServiceUnderTest.findById(0)).isInstanceOf(NotFoundException.class);
	}

	@Test
	void testUpdate_CuentaRepositoryFindByIdReturnsAbsent()
	{
		final CuentaDTO cuenta = new CuentaDTO();
		cuenta.setId(0);
		cuenta.setNumeroCuenta("88881234");
		cuenta.setTipoCuenta(TypeCuenta.AHORROS);
		final ClientEntity cliente = new ClientEntity();
		cliente.setIdentificacion("999991234");

		when(mockCuentaRepository.findById(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> cuentaServiceUnderTest.update(cuenta)).isInstanceOf(NotFoundException.class);
	}

	@Test
	void testUpdatePatch_CuentaRepositoryFindByIdReturnsAbsent()
	{
		final Map<String, Object> results = Map.ofEntries(Map.entry("value", "value"));
		when(mockCuentaRepository.findById(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> cuentaServiceUnderTest.updatePatch(0, results)).isInstanceOf(NotFoundException.class);
	}

	@Test
	void testDelete()
	{
		final CuentaEntity cuenta1 = new CuentaEntity();
		cuenta1.setId(0);
		cuenta1.setNumeroCuenta("999999991");
		cuenta1.setTipoCuenta(TypeCuenta.AHORROS);
		final ClientEntity cliente = new ClientEntity();
		cliente.setIdentificacion("11111111112");
		cuenta1.setCliente(cliente);
		final Optional<CuentaEntity> cuenta = Optional.of(cuenta1);
		when(mockCuentaRepository.findById(0)).thenReturn(cuenta);

		cuentaServiceUnderTest.delete(0);

		verify(mockCuentaRepository).deleteById(0);
	}

	@Test
	void testDelete_CuentaRepositoryFindByIdReturnsAbsent()
	{
		when(mockCuentaRepository.findById(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> cuentaServiceUnderTest.delete(0)).isInstanceOf(NotFoundException.class);
	}

	@Test
	void testFindByNumeroCuenta()
	{
		final CuentaEntity cuenta = new CuentaEntity();
		cuenta.setId(0);
		cuenta.setNumeroCuenta("9999999991");
		cuenta.setTipoCuenta(TypeCuenta.AHORROS);
		final ClientEntity cliente = new ClientEntity();
		cliente.setIdentificacion("123456789101");
		cuenta.setCliente(cliente);
		final Optional<CuentaEntity> expectedResult = Optional.of(cuenta);

		final CuentaEntity cuenta2 = new CuentaEntity();
		cuenta2.setId(0);
		cuenta2.setNumeroCuenta("9999999991");
		cuenta2.setTipoCuenta(TypeCuenta.AHORROS);
		final ClientEntity cliente1 = new ClientEntity();
		cliente1.setIdentificacion("123456789101");
		cuenta2.setCliente(cliente1);
		final Optional<CuentaEntity> cuenta1 = Optional.of(cuenta2);
		when(mockCuentaRepository.findByNumeroCuenta("9999999991")).thenReturn(cuenta1);

		final Optional<CuentaEntity> result = cuentaServiceUnderTest.findByNumeroCuenta("9999999991");

		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void testFindByNumeroCuenta_CuentaRepositoryReturnsAbsent()
	{
		when(mockCuentaRepository.findByNumeroCuenta("99999999911")).thenReturn(Optional.empty());

		final Optional<CuentaEntity> result = cuentaServiceUnderTest.findByNumeroCuenta("99999999911");

		assertThat(result).isEmpty();
	}
}
