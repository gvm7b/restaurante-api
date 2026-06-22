package com.restaurante.backend.service;

import com.restaurante.backend.model.Funcionario;
import com.restaurante.backend.model.Mesa;
import com.restaurante.backend.repository.FuncionarioRepository;
import com.restaurante.backend.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public Mesa salvar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    public List<Mesa> listarTodos() {
        return mesaRepository.findAll();
    }

    public Mesa buscarPorId(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
    }

    public Mesa atualizar(Long id, Mesa mesaAtualizada) {
        Mesa mesa = buscarPorId(id);

        mesa.setNumero(mesaAtualizada.getNumero());
        mesa.setCapacidade(mesaAtualizada.getCapacidade());
        mesa.setStatus(mesaAtualizada.getStatus());

        return mesaRepository.save(mesa);
    }

    public void deletar(Long id) {
        Mesa mesa = buscarPorId(id);
        mesaRepository.delete(mesa);
    }
}
