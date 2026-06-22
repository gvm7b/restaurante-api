package com.restaurante.backend.controller;

import com.restaurante.backend.model.Mesa;
import com.restaurante.backend.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mesa cadastrar(@Valid @RequestBody Mesa mesa) {
        return mesaService.salvar(mesa);
    }

    @GetMapping
    public List<Mesa> listarTodos() {
        return mesaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Mesa buscarPorId(@PathVariable Long id) {
        return mesaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Mesa atualizar(@PathVariable Long id, @Valid @RequestBody Mesa mesa) {
        return mesaService.atualizar(id, mesa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        mesaService.deletar(id);
    }
}
