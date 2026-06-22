package com.restaurante.backend.controller;

import com.restaurante.backend.model.ItemPedido;
import com.restaurante.backend.service.ItemPedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemPedido cadastrar(@Valid @RequestBody ItemPedido itemPedido) {
        return itemPedidoService.salvar(itemPedido);
    }

    @GetMapping
    public List<ItemPedido> listarTodos() {
        return itemPedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ItemPedido buscarPorId(@PathVariable Long id) {
        return itemPedidoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ItemPedido atualizar(@PathVariable Long id, @Valid @RequestBody ItemPedido itemPedido) {
        return itemPedidoService.atualizar(id, itemPedido);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        itemPedidoService.deletar(id);
    }
}
