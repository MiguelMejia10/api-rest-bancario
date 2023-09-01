package com.devsu.api.bancario.implement;

import com.devsu.api.bancario.Exceptions.ErrorException;
import com.devsu.api.bancario.Exceptions.NotFoundException;
import com.devsu.api.bancario.dto.ClienteDTO;
import com.devsu.api.bancario.entity.ClientEntity;
import com.devsu.api.bancario.entity.CuentaEntity;
import com.devsu.api.bancario.entity.MovimientoEntity;
import com.devsu.api.bancario.mapper.ClienteMapper;
import com.devsu.api.bancario.repository.ClienteRepository;
import com.devsu.api.bancario.service.implement.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest
{

	@Mock
	private ClienteRepository mockClienteRepository;

	@InjectMocks
	private ClienteService clienteServiceUnderTest;



	@Test
	void testFindAll_ClienteRepositoryReturnsNoItems()
	{
		when(mockClienteRepository.findAll()).thenReturn(Collections.emptyList());

		final List<ClienteDTO> result = clienteServiceUnderTest.findAll();

		assertThat(result).isEqualTo(Collections.emptyList());
	}


	@Test
	void testFindByClienteId_ClienteRepositoryReturnsAbsent()
	{
		when(mockClienteRepository.findByClientId(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> clienteServiceUnderTest.findByClientId(0)).isInstanceOf(NotFoundException.class);
	}



	@Test
	void testUpdate_ClienteRepositoryFindByClienteIdReturnsAbsent()
	{
		final ClienteDTO cliente = new ClienteDTO();
		cliente.setClienteId(0);
		cliente.setIdentificacion("123454");
		cliente.setPassword("cris09876373");
		when(mockClienteRepository.findByClientId(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> clienteServiceUnderTest.update(cliente)).isInstanceOf(NotFoundException.class);
	}



	@Test
	void testUpdatePatch_ClienteRepositoryFindByClienteIdReturnsAbsent()
	{
		final Map<String, Object> results = Map.ofEntries(Map.entry("value", "value"));
		when(mockClienteRepository.findByClientId(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> clienteServiceUnderTest.updatePatch(0, results)).isInstanceOf(NotFoundException.class);
	}


	@Test
	void testDelete()
	{
		final ClientEntity cliente1 = new ClientEntity();
		cliente1.setClientId(0);
		cliente1.setIdentificacion("jorge");
		cliente1.setPassword("1143407099");
		final CuentaEntity cuenta = new CuentaEntity();
		final MovimientoEntity movimiento = new MovimientoEntity();
		movimiento.setFecha(LocalDateTime.of(2022, 11, 10, 0, 0, 0));
		cuenta.setMovimientos(List.of(movimiento));
		cliente1.setCuentas(List.of(cuenta));
		final Optional<ClientEntity> cliente = Optional.of(cliente1);
		when(mockClienteRepository.findByClientId(0)).thenReturn(cliente);

		clienteServiceUnderTest.delete(0);

		verify(mockClienteRepository).deleteByClientId(0);
	}

	@Test
	void testDelete_ClienteRepositoryFindByClienteIdReturnsAbsent()
	{
		when(mockClienteRepository.findByClientId(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> clienteServiceUnderTest.delete(0)).isInstanceOf(NotFoundException.class);
	}

	@Test
	void testFindByIdentificacion()
	{
		final ClientEntity cliente = new ClientEntity();
		cliente.setClientId(0);
		cliente.setIdentificacion("9999111");
		cliente.setPassword("clavesegura");
		final CuentaEntity cuenta = new CuentaEntity();
		final MovimientoEntity movimiento = new MovimientoEntity();
		movimiento.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
		cuenta.setMovimientos(List.of(movimiento));
		cliente.setCuentas(List.of(cuenta));
		final Optional<ClientEntity> expectedResult = Optional.of(cliente);

		final ClientEntity cliente2 = new ClientEntity();
		cliente2.setClientId(0);
		cliente2.setIdentificacion("9999111");
		cliente2.setPassword("clavesegura");
		final CuentaEntity cuenta1 = new CuentaEntity();
		final MovimientoEntity movimiento1 = new MovimientoEntity();
		movimiento1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
		cuenta1.setMovimientos(List.of(movimiento1));
		cliente2.setCuentas(List.of(cuenta1));
		final Optional<ClientEntity> cliente1 = Optional.of(cliente2);
		when(mockClienteRepository.findByIdentificacion("9999111")).thenReturn(cliente1);

		final Optional<ClientEntity> result = clienteServiceUnderTest.findByIdentificacion("9999111");

		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void testFindByIdentificacion_ClienteRepositoryReturnsAbsent()
	{
		when(mockClienteRepository.findByIdentificacion("999999123")).thenReturn(Optional.empty());

		final Optional<ClientEntity> result = clienteServiceUnderTest.findByIdentificacion("999999123");

		assertThat(result).isEmpty();
	}

	@Test
	void testGetMovByClienteId()
	{
		final ClientEntity expectedResult = new ClientEntity();
		expectedResult.setClientId(0);
		expectedResult.setIdentificacion("88882345");
		expectedResult.setPassword("999");
		final CuentaEntity cuenta = new CuentaEntity();
		final MovimientoEntity movimiento = new MovimientoEntity();
		movimiento.setFecha(LocalDateTime.of(2022, 11, 10, 0, 0, 0));
		cuenta.setMovimientos(List.of(movimiento));
		expectedResult.setCuentas(List.of(cuenta));

		final ClientEntity cliente1 = new ClientEntity();
		cliente1.setClientId(0);
		cliente1.setIdentificacion("88882345");
		cliente1.setPassword("999");
		final CuentaEntity cuenta1 = new CuentaEntity();
		final MovimientoEntity movimiento1 = new MovimientoEntity();
		movimiento1.setFecha(LocalDateTime.of(2022, 11, 10, 0, 0, 0));
		cuenta1.setMovimientos(List.of(movimiento1));
		cliente1.setCuentas(List.of(cuenta1));
		final Optional<ClientEntity> cliente = Optional.of(cliente1);
		when(mockClienteRepository.findById(0)).thenReturn(cliente);

		final ClientEntity result = clienteServiceUnderTest.getMovByClienteId(0, LocalDate.of(2022, 11, 10), LocalDate.of(2022, 11, 10));

		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void testGetMovByClienteId_ClienteRepositoryReturnsAbsent()
	{
		when(mockClienteRepository.findById(0)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> clienteServiceUnderTest.getMovByClienteId(0, LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 1, 1))).isInstanceOf(NotFoundException.class);
	}
}
