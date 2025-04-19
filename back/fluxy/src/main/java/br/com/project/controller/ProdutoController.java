package br.com.project.controller;

import br.com.project.model.Produto;
import br.com.project.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public void salvar(@RequestBody Produto produto) {
        produtoService.salvar(produto);
    }

    @GetMapping("/{id}")
    public Optional<Produto> buscarPorId(@PathVariable Integer id) {
        return produtoService.buscarPorId(id);
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Produto produto) {
        produto.setIdProduto(id);
        produtoService.atualizar(produto);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        produtoService.deletarPorId(id);
    }
}
