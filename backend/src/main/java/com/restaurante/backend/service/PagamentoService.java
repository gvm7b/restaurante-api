package com.restaurante.backend.service;

import com.restaurante.backend.model.Pagamento;
import com.restaurante.backend.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Pagamento atualizar(Long id, Pagamento pagamentoAtualizado) {
        Pagamento pagamento = buscarPorId(id);

        pagamento.setPedido(pagamentoAtualizado.getPedido());
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
}
