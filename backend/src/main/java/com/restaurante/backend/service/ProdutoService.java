package com.restaurante.backend.service;

import com.restaurante.backend.exception.ResourceNotFoundException;
import com.restaurante.backend.model.Categoria;
import com.restaurante.backend.model.Produto;
import com.restaurante.backend.repository.CategoriaRepository;
import com.restaurante.backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto salvar(Produto produto) {
        produto.setCategoria(buscarCategoriaInformada(produto));
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto nao encontrado"));
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = buscarPorId(id);

        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setEstoque(produtoAtualizado.getEstoque());
        produto.setCategoria(buscarCategoriaInformada(produtoAtualizado));

        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    private Categoria buscarCategoriaInformada(Produto produto) {
        if (produto.getCategoria() == null || produto.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("Informe uma categoria existente para o produto");
        }

        return categoriaRepository.findById(produto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria informada nao existe"));
    }
}
