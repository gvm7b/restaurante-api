package com.restaurante.backend.service;

import com.restaurante.backend.model.Pedido;
import com.restaurante.backend.model.Produto;
import com.restaurante.backend.repository.PedidoRepository;
import com.restaurante.backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = buscarPorId(id);

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
}
