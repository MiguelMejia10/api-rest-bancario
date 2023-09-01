package com.devsu.api.bancario.entity;

import com.devsu.api.bancario.enums.TypeCuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas")
public class CuentaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Numero de cuenta debe ser ingresado")
    @Pattern(regexp = "^[0-9]{6,11}$", message = "Por favor verifique el numero de cuenta")
    @Column(name = "numero_cuenta", unique = true)
    private String numeroCuenta;

    @NotNull(message = " Tipo de cuenta debe ser ingresada Ahorros / Corriente")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private TypeCuenta tipoCuenta;

    @NotNull(message = "Saldo inicial debe ser ingresado")
    @Column(name = "saldo_inicial")
    private double saldoInicial;

    @NotNull(message = "Estado debe ser ingresado y solo puede ser true o false")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClientEntity cliente;

    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
    private List<MovimientoEntity> movimientos;

}
