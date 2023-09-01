package com.devsu.api.bancario.service.implement;

import com.devsu.api.bancario.Exceptions.ErrorException;
import com.devsu.api.bancario.Exceptions.ExceptionsConstants;
import com.devsu.api.bancario.Exceptions.NotFoundException;
import com.devsu.api.bancario.dto.CuentaDTO;
import com.devsu.api.bancario.entity.ClientEntity;
import com.devsu.api.bancario.entity.CuentaEntity;
import com.devsu.api.bancario.enums.TypeCuenta;
import com.devsu.api.bancario.mapper.CuentaMapper;
import com.devsu.api.bancario.repository.CuentaRepository;
import com.devsu.api.bancario.service.ICuentaService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class CuentaService implements ICuentaService
{
	public static final String TIPO_CUENTA = "tipoCuenta";
	public static final String NUMERO_CUENTA = "numeroCuenta";

	@Autowired
	private CuentaRepository cuentaRepository;

	@Autowired
	private ClienteService clienteService;

	@Override
	public List<CuentaDTO> findAll()
	{
		return cuentaRepository.findAll().stream().map(CuentaMapper.INSTANCE::cuentaToCuentaDTO).collect(Collectors.toList());
	}

	@Override
	public CuentaDTO findById(final Integer id)
	{
		Optional<CuentaEntity> cuenta = cuentaRepository.findById(id);
		if (cuenta.isPresent())
		{
			return cuenta.map(CuentaMapper.INSTANCE::cuentaToCuentaDTO).get();
		}
		else
		{
			log.error(ExceptionsConstants.NUMERO_DE_CUENTA_NO_EXISTE, id);
			throw new NotFoundException(String.format(ExceptionsConstants.NUMERO_DE_CUENTA_NO_EXISTE, id));
		}
	}

	@Override
	public CuentaDTO create(final CuentaDTO cuentaDTO)
	{
		log.info(ExceptionsConstants.CREANDO_CUENTA);
		Optional<ClientEntity> cliente = clienteService.findByIdentificacion(cuentaDTO.getCliente().getIdentificacion());
		if (cliente.isEmpty())
		{
			log.error(ExceptionsConstants.CLIENTE_NO_EXISTE, cuentaDTO.getCliente());
			throw new NotFoundException(String.format(ExceptionsConstants.CLIENTE_NO_EXISTE, cuentaDTO.getCliente()));
		}
		if (findByNumeroCuenta(cuentaDTO.getNumeroCuenta()).isPresent())
		{
			log.error(ExceptionsConstants.NUMERO_DE_CUENTA_YA_EXISTE, cuentaDTO.getNumeroCuenta());
			throw new ErrorException(
					String.format(ExceptionsConstants.NUMERO_DE_CUENTA_YA_EXISTE, cuentaDTO.getNumeroCuenta()));
		}
		CuentaEntity cuenta = CuentaMapper.INSTANCE.cuentaDTOToCuenta(cuentaDTO);
		cuenta.setCliente(cliente.get());
		return CuentaMapper.INSTANCE.cuentaToCuentaDTO(cuentaRepository.save(cuenta));
	}

	@Override
	public CuentaDTO update(final CuentaDTO cuentaDTO)
	{
		log.info(ExceptionsConstants.ACTUALIZANDO_CUENTA);
		Optional<CuentaEntity> updateCuenta = cuentaRepository.findById(cuentaDTO.getId());
		if (updateCuenta.isPresent())
		{
			if (!cuentaDTO.getNumeroCuenta().equals(updateCuenta.get().getNumeroCuenta()) && Objects.nonNull(
					findByNumeroCuenta(cuentaDTO.getNumeroCuenta())))
			{
				log.error(ExceptionsConstants.NUMERO_DE_CUENTA_YA_EXISTE, cuentaDTO.getNumeroCuenta());
				throw new ErrorException(
						String.format(ExceptionsConstants.NUMERO_DE_CUENTA_YA_EXISTE, cuentaDTO.getNumeroCuenta()));
			}
			Optional<ClientEntity> cliente = clienteService.findByIdentificacion(cuentaDTO.getCliente().getIdentificacion());
			final CuentaEntity cuenta = CuentaMapper.INSTANCE.cuentaDTOToCuenta(cuentaDTO);
			cuenta.setCliente(cliente.get());
			return CuentaMapper.INSTANCE.cuentaToCuentaDTO(cuentaRepository.save(cuenta));
		}
		else
		{
			log.error(ExceptionsConstants.NUMERO_DE_CUENTA_NO_EXISTE, cuentaDTO.getId());
			throw new NotFoundException(String.format(ExceptionsConstants.NUMERO_DE_CUENTA_NO_EXISTE, cuentaDTO.getId()));
		}
	}

	@Override
	public CuentaDTO updatePatch(final Integer id, final Map<String, Object> results)
	{
		log.info(ExceptionsConstants.ACTUALIZANDO_CUENTA);
		Optional<CuentaEntity> cuenta = cuentaRepository.findById(id);
		if (cuenta.isPresent())
		{

			results.forEach((j, q) -> {
				if (j.equals(NUMERO_CUENTA) && !q.equals(cuenta.get().getNumeroCuenta()) && findByNumeroCuenta(
						q.toString()).isPresent())
				{
					log.error(ExceptionsConstants.NUMERO_DE_CUENTA_YA_EXISTE, q.toString());
					throw new ErrorException(String.format(ExceptionsConstants.NUMERO_DE_CUENTA_YA_EXISTE, q.toString()));
				}

				if (j.equals(TIPO_CUENTA))
				{
					q = TypeCuenta.valueOf(q.toString());
				}

				Field field = ReflectionUtils.findField(CuentaEntity.class, j);
				field.setAccessible(true);
				ReflectionUtils.setField(field, cuenta.get(), q);
			});
			return CuentaMapper.INSTANCE.cuentaToCuentaDTO(cuentaRepository.save(cuenta.get()));
		}
		else
		{
			log.error(ExceptionsConstants.NUMERO_DE_CUENTA_NO_EXISTE, id);
			throw new NotFoundException(String.format(ExceptionsConstants.NUMERO_DE_CUENTA_NO_EXISTE, id));
		}
	}

	@Override
	public void delete(final Integer id)
	{
		cuentaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(ExceptionsConstants.NUMERO_DE_CUENTA_NO_EXISTE, id)));
		cuentaRepository.deleteById(id);
		log.info(ExceptionsConstants.CUENTA_ELIMINADA, id);
	}

	@Override
	public Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta)
	{
		return cuentaRepository.findByNumeroCuenta(numeroCuenta);
	}
}
