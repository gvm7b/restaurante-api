package com.restaurante.backend.service;

import com.restaurante.backend.exception.ResourceNotFoundException;
import com.restaurante.backend.model.Pagamento;
import com.restaurante.backend.model.Pedido;
import com.restaurante.backend.repository.PagamentoRepository;
import com.restaurante.backend.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, PedidoRepository pedidoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Pagamento salvar(Pagamento pagamento) {
        pagamento.setPedido(buscarPedidoInformado(pagamento));
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento nao encontrado"));
    }

    public Pagamento atualizar(Long id, Pagamento pagamentoAtualizado) {
        Pagamento pagamento = buscarPorId(id);

        pagamento.setPedido(buscarPedidoInformado(pagamentoAtualizado));
        pagamento.setFormaPagamento(pagamentoAtualizado.getFormaPagamento());
        pagamento.setValorPago(pagamentoAtualizado.getValorPago());
        pagamento.setDataPagamento(pagamentoAtualizado.getDataPagamento());
        pagamento.setStatus(pagamentoAtualizado.getStatus());

        return pagamentoRepository.save(pagamento);
    }

    public void deletar(Long id) {
        Pagamento pagamento = buscarPorId(id);
        pagamentoRepository.delete(pagamento);
    }

    private Pedido buscarPedidoInformado(Pagamento pagamento) {
        if (pagamento.getPedido() == null || pagamento.getPedido().getIdPedido() == null) {
            throw new IllegalArgumentException("Informe um pedido existente para o pagamento");
        }

        return pedidoRepository.findById(pagamento.getPedido().getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido informado nao existe"));
    }
}
