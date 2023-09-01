package com.devsu.api.bancario.service.implement;


import com.devsu.api.bancario.Exceptions.ErrorException;
import com.devsu.api.bancario.Exceptions.ExceptionsConstants;
import com.devsu.api.bancario.Exceptions.InternalServerErrorException;
import com.devsu.api.bancario.Exceptions.NotFoundException;
import com.devsu.api.bancario.dto.ClienteDTO;
import com.devsu.api.bancario.entity.ClientEntity;
import com.devsu.api.bancario.entity.MovimientoEntity;
import com.devsu.api.bancario.enums.TypeGenero;
import com.devsu.api.bancario.mapper.ClientMapper;
import com.devsu.api.bancario.repository.ClienteRepository;
import com.devsu.api.bancario.service.IClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class ClienteService implements IClienteService
{
	public static final String CONTRASENA = "contrasena";
	public static final String GENERO = "genero";
	public static final String EDAD = "edad";
	public static final String IDENTIFICACION = "identificacion";

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public List<ClienteDTO> findAll()
	{
		return clienteRepository.findAll().stream().map(ClientMapper.INSTANCE::clienteToClienteDTO).collect(Collectors.toList());
	}

	@Override
	public ClienteDTO findByClientId(final Integer clientId)
	{
		Optional<ClientEntity> cliente = clienteRepository.findByClientId(clientId);
		if (cliente.isPresent())
		{
			return cliente.map(ClientMapper.INSTANCE::clienteToClienteDTO).get();
		}
		else
		{
			log.error(ExceptionsConstants.CLIENTE_NO_EXISTE, clientId);
			throw new NotFoundException(String.format(ExceptionsConstants.CLIENTE_NO_EXISTE, clientId));
		}
	}

	@Override
	public ClienteDTO create(final ClienteDTO clienteDTO)
	{
		log.info(ExceptionsConstants.CREANDO_CLIENTE);
		if (findByIdentificacion(clienteDTO.getIdentificacion()).isPresent())
		{
			log.error(ExceptionsConstants.CLIENTE_YA_EXISTE, clienteDTO.getIdentificacion());
			throw new ErrorException(
					String.format(ExceptionsConstants.CLIENTE_YA_EXISTE, clienteDTO.getIdentificacion()));
		}
		clienteDTO.setPassword(cifrarPassword(clienteDTO.getPassword()));
		return ClientMapper.INSTANCE.clienteToClienteDTO(
				clienteRepository.save(ClientMapper.INSTANCE.clienteDTOToCliente(clienteDTO)));
	}

	@Override
	public ClienteDTO update(final ClienteDTO clienteDTO)
	{
		log.info(ExceptionsConstants.ACTUALIZANDO_CLIENTE);
		Optional<ClientEntity> updatecliente = clienteRepository.findByClientId(clienteDTO.getClienteId());
		if (updatecliente.isPresent())
		{
			if (!clienteDTO.getIdentificacion().equals(updatecliente.get().getIdentificacion()) && findByIdentificacion(
					clienteDTO.getIdentificacion()).isPresent())
			{
				log.error(ExceptionsConstants.CLIENTE_YA_EXISTE, clienteDTO.getIdentificacion());
				throw new ErrorException(String.format(ExceptionsConstants.CLIENTE_YA_EXISTE,
						clienteDTO.getIdentificacion()));
			}
			clienteDTO.setPassword(cifrarPassword(clienteDTO.getPassword()));
			return ClientMapper.INSTANCE.clienteToClienteDTO(
					clienteRepository.save(ClientMapper.INSTANCE.clienteDTOToCliente(clienteDTO)));
		}
		else
		{
			log.error(ExceptionsConstants.CLIENTE_YA_EXISTE, clienteDTO.getClienteId());
			throw new NotFoundException(String.format(ExceptionsConstants.CLIENTE_NO_EXISTE, clienteDTO.getClienteId()));
		}
	}

	@Override
	public ClienteDTO updatePatch(final Integer clienteId, final Map<String, Object> results)
	{
		Optional<ClientEntity> cliente = clienteRepository.findByClientId(clienteId);
		if (cliente.isPresent())
		{
			results.forEach((j, q) -> {
				if (j.equals(IDENTIFICACION) && !q.equals(cliente.get().getIdentificacion()) && findByIdentificacion(
						q.toString()).isPresent())
				{
					log.error(ExceptionsConstants.CLIENTE_YA_EXISTE, q.toString());
					throw new ErrorException(
							String.format(ExceptionsConstants.CLIENTE_YA_EXISTE, q.toString()));
				}

				if (j.equals(EDAD))
				{
					q = Byte.parseByte(q.toString());
				}
				if (j.equals(GENERO))
				{
					q = TypeGenero.valueOf(q.toString());
				}
				if (j.equals(CONTRASENA))
				{
					q = cifrarPassword(q.toString());
				}
				Field field = ReflectionUtils.findField(ClientEntity.class, j);
				field.setAccessible(true);
				ReflectionUtils.setField(field, cliente.get(), q);
			});
			return ClientMapper.INSTANCE.clienteToClienteDTO(clienteRepository.save(cliente.get()));
		}
		else
		{
			log.error(ExceptionsConstants.CLIENTE_NO_EXISTE, clienteId);
			throw new NotFoundException(String.format(ExceptionsConstants.CLIENTE_NO_EXISTE, clienteId));
		}

	}

	@Override
	public void delete(final Integer clienteId)
	{
		clienteRepository.findByClientId(clienteId)
				.orElseThrow(() -> new NotFoundException(String.format(ExceptionsConstants.CLIENTE_NO_EXISTE, clienteId)));
		clienteRepository.deleteByClientId(clienteId);
		log.info(ExceptionsConstants.CLIENTE_ELIMINADO, clienteId);

	}

	@Override
	public Optional<ClientEntity> findByIdentificacion(final String identificacion)
	{
		return clienteRepository.findByIdentificacion(identificacion);
	}

	@Override
	public ClientEntity getMovByClienteId(final Integer clienteId, final LocalDate fechaInicial, final LocalDate fechaFinal)
	{
		ClientEntity cliente = clienteRepository.findById(clienteId).orElseThrow(() -> {
			log.warn(ExceptionsConstants.CLIENTE_NO_EXISTE, clienteId);
			return new NotFoundException(String.format(ExceptionsConstants.CLIENTE_NO_EXISTE, clienteId));
		});

		cliente.getCuentas().forEach(cuenta -> {
			List<MovimientoEntity> movimientoList = cuenta.getMovimientos().stream()
					.filter(m -> m.getFecha().toLocalDate().isEqual(fechaInicial) || m.getFecha().toLocalDate()
							.isEqual(fechaFinal) || (m.getFecha().toLocalDate().isAfter(fechaInicial) && m.getFecha()
							.toLocalDate().isBefore(fechaFinal))).collect(Collectors.toList());
			cuenta.setMovimientos(movimientoList);
		});
		return cliente;
	}

	private String cifrarPassword(final String password)
	{
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("SHA-256");
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("Error cifrando la contraseña");
			throw new InternalServerErrorException("Error cifrando la contraseña");
		}

		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();

		for (byte b : hash)
		{
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}
}
