package br.com.project.service;

import br.com.project.repository.ProdutoCategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoCategoriaService {

    private final ProdutoCategoriaRepository produtoCategoriaRepository;

    public ProdutoCategoriaService(ProdutoCategoriaRepository produtoCategoriaRepository) {
        this.produtoCategoriaRepository = produtoCategoriaRepository;
    }

    public void salvar(Integer idProduto, String codigoCategoria) {
        produtoCategoriaRepository.save(idProduto, codigoCategoria);
    }
}
