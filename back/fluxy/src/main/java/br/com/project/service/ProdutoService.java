package br.com.project.service;

import br.com.project.model.Produto;
import br.com.project.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void salvar(Produto produto) {
        produtoRepository.save(produto);
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public void atualizar(Produto produto) {
        produtoRepository.update(produto);
    }

    public void deletarPorId(Integer id) {
        produtoRepository.deleteById(id);
    }
}
