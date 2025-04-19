package br.com.project.controller;

import br.com.project.model.ProdutoCategoria;
import br.com.project.service.ProdutoCategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto-categoria")
public class ProdutoCategoriaController {

    private final ProdutoCategoriaService produtoCategoriaService;

    public ProdutoCategoriaController(ProdutoCategoriaService produtoCategoriaService) {
        this.produtoCategoriaService = produtoCategoriaService;
    }

    @PostMapping
    public void salvar(@RequestBody ProdutoCategoria produtoCategoria) {
        produtoCategoriaService.salvar(produtoCategoria);
    }

    @GetMapping
    public List<ProdutoCategoria> listarTodos() {
        return produtoCategoriaService.listarTodos();
    }

    @DeleteMapping
    public void deletar(@RequestParam Integer produtoId, @RequestParam String categoriaCodigo) {
        produtoCategoriaService.deletarPorIds(produtoId, categoriaCodigo);
    }
}
