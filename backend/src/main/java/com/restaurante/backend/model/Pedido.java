package com.restaurante.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "Cliente e obrigatorio")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    @NotNull(message = "Funcionario e obrigatorio")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_mesa", nullable = false)
    @NotNull(message = "Mesa e obrigatoria")
    private Mesa mesa;

    @Column(name = "data_pedido", nullable = false)
    @NotNull(message = "Data do pedido e obrigatoria")
    private LocalDateTime dataPedido;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Status do pedido e obrigatorio")
    @Size(max = 30, message = "Status do pedido deve ter no maximo 30 caracteres")
    private String status;
}
