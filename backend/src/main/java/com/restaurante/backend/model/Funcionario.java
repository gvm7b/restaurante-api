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

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome do funcionario e obrigatorio")
    @Size(max = 100, message = "Nome do funcionario deve ter no maximo 100 caracteres")
    private String nome;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Cargo do funcionario e obrigatorio")
    @Size(max = 50, message = "Cargo deve ter no maximo 50 caracteres")
    private String cargo;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Salario e obrigatorio")
    @Positive(message = "Salario deve ser maior que zero")
    private BigDecimal salario;
}
