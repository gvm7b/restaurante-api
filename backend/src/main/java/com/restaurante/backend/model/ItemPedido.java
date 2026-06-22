package com.restaurante.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long idItem;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    @NotNull(message = "Pedido e obrigatorio")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    @NotNull(message = "Produto e obrigatorio")
    private Produto produto;

    @Column(nullable = false)
    @NotNull(message = "Quantidade e obrigatoria")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Preco unitario e obrigatorio")
    @Positive(message = "Preco unitario deve ser maior que zero")
    private BigDecimal precoUnitario;

}
