package com.devsu.api.bancario.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class ClientEntity extends PersonaEntity implements Serializable {

    @NotEmpty(message = "Contraseña debe ser ingresado")
    private String password;

    @NotNull(message = "Estado debe ser ingresado y debe ser true o false")
    private String estado;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CuentaEntity> cuentas;

}
