package com.restaurante.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long idProduto;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome do produto e obrigatorio")
    @Size(max = 100, message = "Nome do produto deve ter no maximo 100 caracteres")
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Preco e obrigatorio")
    @Positive(message = "Preco deve ser maior que zero")
    private BigDecimal preco;

    @Column(nullable = false)
    @NotNull(message = "Estoque e obrigatorio")
    @PositiveOrZero(message = "Estoque nao pode ser negativo")
    private Integer estoque;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @NotNull(message = "Categoria e obrigatoria")
    private Categoria categoria;
}
