package com.devsu.api.bancario.entity;


import com.devsu.api.bancario.enums.TypeGenero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PersonaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer clientId;

    @NotEmpty(message = "Nombre debe ser ingresado")
    private String nombre;

    @NotNull(message = "Genero debe ser ingresado MACULINO, FEMENINO O OTRO")
    @Enumerated(EnumType.STRING)
    private TypeGenero genero;

    @NotNull(message = "Edad debe ser ingresado")
    @Range(min=1, max=150,message = "Edad minima 1, edad maxima 150")
    private byte edad;

    @NotEmpty(message = "Identificacion debe ser ingresado")
    @Column(unique = true)
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Identificación incorrecta y debe tener entre 10 y 11 numeros")
    private String identificacion;

    @NotEmpty(message = "Direccion debe ser ingresado")
    private String direccion;

    @NotEmpty(message = "Telefono debe ser ingresado")
    @Pattern(regexp = "^[0-9]{9,10}$", message = "Telefono invalido y debe tener entre 9 y 10 numeros")
    private String telefono;

}
