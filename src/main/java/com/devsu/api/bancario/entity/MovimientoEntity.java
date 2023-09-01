package com.devsu.api.bancario.entity;


import com.devsu.api.bancario.enums.TypeMovimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimientos")
public class MovimientoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime fecha;

    @NotNull(message = "Tipo movimiento debe ser ingresado AHORROS O CORRIENTE")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private TypeMovimiento tipoMovimiento;

    @NotNull(message = "Valor es un campo obligatorio")
    private Double valor;

    private Double saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_cuenta")
    private CuentaEntity cuenta;


}
