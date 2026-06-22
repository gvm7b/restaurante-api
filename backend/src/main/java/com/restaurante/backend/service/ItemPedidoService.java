package com.restaurante.backend.service;

import com.restaurante.backend.model.ItemPedido;
import com.restaurante.backend.model.Pedido;
import com.restaurante.backend.repository.ItemPedidoRepository;
import com.restaurante.backend.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public ItemPedido salvar(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    public List<ItemPedido> listarTodos() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido buscarPorId(Long id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item pedido não encontrada"));
    }

    public ItemPedido atualizar(Long id, ItemPedido itemPedidoAtualizado) {
        ItemPedido itemPedido = buscarPorId(id);

        itemPedido.setPedido(itemPedidoAtualizado.getPedido());
        itemPedido.setProduto(itemPedidoAtualizado.getProduto());
        itemPedido.setQuantidade(itemPedidoAtualizado.getQuantidade());
        itemPedido.setPrecoUnitario(itemPedidoAtualizado.getPrecoUnitario());

        return itemPedidoRepository.save(itemPedido);
    }

    public void deletar(Long id) {
        ItemPedido itemPedido = buscarPorId(id);
        itemPedidoRepository.delete(itemPedido);
    }
}
