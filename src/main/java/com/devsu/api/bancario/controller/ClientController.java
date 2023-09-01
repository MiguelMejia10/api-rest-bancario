package com.devsu.api.bancario.controller;

import com.devsu.api.bancario.Exceptions.BadRequestException;
import com.devsu.api.bancario.Exceptions.ExceptionsConstants;
import com.devsu.api.bancario.dto.ClienteDTO;
import com.devsu.api.bancario.service.IClienteService;
import com.devsu.api.bancario.service.implement.ClienteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClienteService clienteService;


    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll()
    {
        List<ClienteDTO> clientes = clienteService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> findByClienteId(@PathVariable final Integer clientId)
    {
        return ResponseEntity.ok(clienteService.findByClientId(clientId));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody final ClienteDTO clienteDTO, BindingResult bindingResult)
    {
        StringBuilder errors = new StringBuilder();
        if (bindingResult.hasErrors())
        {
            bindingResult.getAllErrors().forEach(objectError -> {
                errors.append(objectError.getDefaultMessage());
                errors.append("\n");
            });
            throw new BadRequestException(errors.toString());
        }
        ClienteDTO newclienteDTO = clienteService.create(clienteDTO);
        log.info(ExceptionsConstants.CLIENTE_CREADO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newclienteDTO);
    }

    @PutMapping
    public ResponseEntity<ClienteDTO> update(@RequestBody final ClienteDTO clienteDTO,  BindingResult bindingResult)
    {
        StringBuilder errors = new StringBuilder();
        if (bindingResult.hasErrors())
        {
            bindingResult.getAllErrors().forEach(objectError -> {
                errors.append(objectError.getDefaultMessage());
                errors.append("\n");
            });
            throw new BadRequestException(errors.toString());
        }
        ClienteDTO clienteActualizado = clienteService.update(clienteDTO);
        log.info(ExceptionsConstants.CLIENTE_ACTUALIZADO);
        return ResponseEntity.status(HttpStatus.OK).body(clienteActualizado);
    }

    @PatchMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> updatePatch(@PathVariable final Integer clienteId,@RequestBody final Map<String, Object> results)
    {
        ClienteDTO clienteActualizado = clienteService.updatePatch(clienteId,results);
        return ResponseEntity.status(HttpStatus.OK).body(clienteActualizado);
    }

    @DeleteMapping("/{clienteId}")
    public void delete(@PathVariable Integer clienteId)
    {
        clienteService.delete(clienteId);
    }

}
