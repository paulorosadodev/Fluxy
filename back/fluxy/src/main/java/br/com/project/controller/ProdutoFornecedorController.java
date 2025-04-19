package br.com.project.controller;

import br.com.project.model.ProdutoFornecedor;
import br.com.project.service.ProdutoFornecedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto-fornecedor")
public class ProdutoFornecedorController {

    private final ProdutoFornecedorService produtoFornecedorService;

    public ProdutoFornecedorController(ProdutoFornecedorService produtoFornecedorService) {
        this.produtoFornecedorService = produtoFornecedorService;
    }

    @PostMapping
    public void salvar(@RequestBody ProdutoFornecedor produtoFornecedor) {
        produtoFornecedorService.salvar(produtoFornecedor);
    }

    @GetMapping
    public List<ProdutoFornecedor> listarTodos() {
        return produtoFornecedorService.listarTodos();
    }

    @DeleteMapping
    public void deletar(@RequestParam Integer fornecedorId, @RequestParam Integer produtoId) {
        produtoFornecedorService.deletarPorIds(fornecedorId, produtoId);
    }
}
