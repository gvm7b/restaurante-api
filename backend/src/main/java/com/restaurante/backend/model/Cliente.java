package com.restaurante.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome do cliente e obrigatorio")
    @Size(max = 100, message = "Nome do cliente deve ter no maximo 100 caracteres")
    private String nome;

    @Column(length = 20)
    @Size(max = 20, message = "Telefone deve ter no maximo 20 caracteres")
    private String telefone;

    @Column(length = 100)
    @Email(message = "Email deve ser valido")
    @Size(max = 100, message = "Email deve ter no maximo 100 caracteres")
    private String email;

    @Column(columnDefinition = "TEXT")
    private String endereco;
}
