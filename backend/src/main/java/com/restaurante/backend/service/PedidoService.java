package com.restaurante.backend.service;

import com.restaurante.backend.exception.ResourceNotFoundException;
import com.restaurante.backend.model.Cliente;
import com.restaurante.backend.model.Funcionario;
import com.restaurante.backend.model.Mesa;
import com.restaurante.backend.model.Pedido;
import com.restaurante.backend.repository.ClienteRepository;
import com.restaurante.backend.repository.FuncionarioRepository;
import com.restaurante.backend.repository.MesaRepository;
import com.restaurante.backend.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private static final String STATUS_MESA_RESERVADA = "Reservado";

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final MesaRepository mesaRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            FuncionarioRepository funcionarioRepository,
            MesaRepository mesaRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.mesaRepository = mesaRepository;
    }

    public Pedido salvar(Pedido pedido) {
        preencherRelacionamentos(pedido);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido nao encontrado"));
    }

    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = buscarPorId(id);
        preencherRelacionamentos(pedidoAtualizado);

        pedido.setCliente(pedidoAtualizado.getCliente());
        pedido.setFuncionario(pedidoAtualizado.getFuncionario());
        pedido.setMesa(pedidoAtualizado.getMesa());
        pedido.setDataPedido(pedidoAtualizado.getDataPedido());
        pedido.setStatus(pedidoAtualizado.getStatus());

        return pedidoRepository.save(pedido);
    }

    public void deletar(Long id) {
        Pedido pedido = buscarPorId(id);
        pedidoRepository.delete(pedido);
    }

    private void preencherRelacionamentos(Pedido pedido) {
        pedido.setCliente(buscarClienteInformado(pedido));
        pedido.setFuncionario(buscarFuncionarioInformado(pedido));
        pedido.setMesa(buscarMesaInformada(pedido));
    }

    private Cliente buscarClienteInformado(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getIdCliente() == null) {
            throw new IllegalArgumentException("Informe um cliente existente para o pedido");
        }

        return clienteRepository.findById(pedido.getCliente().getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente informado nao existe"));
    }

    private Funcionario buscarFuncionarioInformado(Pedido pedido) {
        if (pedido.getFuncionario() == null || pedido.getFuncionario().getIdFuncionario() == null) {
            throw new IllegalArgumentException("Informe um funcionario existente para o pedido");
        }

        return funcionarioRepository.findById(pedido.getFuncionario().getIdFuncionario())
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario informado nao existe"));
    }

    private Mesa buscarMesaInformada(Pedido pedido) {
        if (pedido.getMesa() == null || pedido.getMesa().getIdMesa() == null) {
            throw new IllegalArgumentException("Informe uma mesa existente para o pedido");
        }

        Mesa mesa = mesaRepository.findById(pedido.getMesa().getIdMesa())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa informada nao existe"));

        mesa.setStatus(STATUS_MESA_RESERVADA);
        return mesaRepository.save(mesa);
    }
}
