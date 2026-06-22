package com.restaurante.backend.service;

import com.restaurante.backend.exception.ResourceNotFoundException;
import com.restaurante.backend.model.ItemPedido;
import com.restaurante.backend.model.Pedido;
import com.restaurante.backend.model.Produto;
import com.restaurante.backend.repository.ItemPedidoRepository;
import com.restaurante.backend.repository.PedidoRepository;
import com.restaurante.backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(
            ItemPedidoRepository itemPedidoRepository,
            PedidoRepository pedidoRepository,
            ProdutoRepository produtoRepository
    ) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public ItemPedido salvar(ItemPedido itemPedido) {
        preencherRelacionamentos(itemPedido);
        return itemPedidoRepository.save(itemPedido);
    }

    public List<ItemPedido> listarTodos() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido buscarPorId(Long id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do pedido nao encontrado"));
    }

    public ItemPedido atualizar(Long id, ItemPedido itemPedidoAtualizado) {
        ItemPedido itemPedido = buscarPorId(id);
        preencherRelacionamentos(itemPedidoAtualizado);

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

    private void preencherRelacionamentos(ItemPedido itemPedido) {
        itemPedido.setPedido(buscarPedidoInformado(itemPedido));
        itemPedido.setProduto(buscarProdutoInformado(itemPedido));
    }

    private Pedido buscarPedidoInformado(ItemPedido itemPedido) {
        if (itemPedido.getPedido() == null || itemPedido.getPedido().getIdPedido() == null) {
            throw new IllegalArgumentException("Informe um pedido existente para o item");
        }

        return pedidoRepository.findById(itemPedido.getPedido().getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido informado nao existe"));
    }

    private Produto buscarProdutoInformado(ItemPedido itemPedido) {
        if (itemPedido.getProduto() == null || itemPedido.getProduto().getIdProduto() == null) {
            throw new IllegalArgumentException("Informe um produto existente para o item");
        }

        return produtoRepository.findById(itemPedido.getProduto().getIdProduto())
                .orElseThrow(() -> new ResourceNotFoundException("Produto informado nao existe"));
    }
}
