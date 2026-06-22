package com.restaurante.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    @NotNull(message = "Pedido e obrigatorio")
    private Pedido pedido;

    @Column(name = "forma_pagamento", nullable = false, length = 50)
    @NotBlank(message = "Forma de pagamento e obrigatoria")
    @Size(max = 50, message = "Forma de pagamento deve ter no maximo 50 caracteres")
    private String formaPagamento;

    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Valor pago e obrigatorio")
    @Positive(message = "Valor pago deve ser maior que zero")
    private BigDecimal valorPago;

    @Column(name = "data_pagamento", nullable = false)
    @NotNull(message = "Data de pagamento e obrigatoria")
    private LocalDateTime dataPagamento;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Status do pagamento e obrigatorio")
    @Size(max = 30, message = "Status do pagamento deve ter no maximo 30 caracteres")
    private String status;
}
