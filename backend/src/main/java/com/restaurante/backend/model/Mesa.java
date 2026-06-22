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

@Entity
@Table(name = "mesa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa")
    private Long idMesa;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Numero da mesa e obrigatorio")
    @Positive(message = "Numero da mesa deve ser maior que zero")
    private Integer numero;

    @Column(nullable = false)
    @NotNull(message = "Capacidade da mesa e obrigatoria")
    @Positive(message = "Capacidade da mesa deve ser maior que zero")
    private Integer capacidade;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Status da mesa e obrigatorio")
    @Size(max = 30, message = "Status da mesa deve ter no maximo 30 caracteres")
    private String status;
}
