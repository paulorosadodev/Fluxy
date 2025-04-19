package br.com.project.service;

import br.com.project.model.ProdutoCategoria;
import br.com.project.repository.ProdutoCategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoCategoriaService {

    private final ProdutoCategoriaRepository produtoCategoriaRepository;

    public ProdutoCategoriaService(ProdutoCategoriaRepository produtoCategoriaRepository) {
        this.produtoCategoriaRepository = produtoCategoriaRepository;
    }

    public void salvar(ProdutoCategoria produtoCategoria) {
        produtoCategoriaRepository.save(produtoCategoria);
    }

    public List<ProdutoCategoria> listarTodos() {
        return produtoCategoriaRepository.findAll();
    }

    public void deletarPorIds(Integer produtoId, String categoriaCodigo) {
        produtoCategoriaRepository.deleteByIds(produtoId, categoriaCodigo);
    }
}
