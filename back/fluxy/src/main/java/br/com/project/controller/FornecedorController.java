package br.com.project.controller;

import br.com.project.model.Fornecedor;
import br.com.project.service.FornecedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public void salvar(@RequestBody Fornecedor fornecedor) {
        fornecedorService.salvar(fornecedor);
    }

    @GetMapping("/{id}")
    public Optional<Fornecedor> buscarPorId(@PathVariable Integer id) {
        return fornecedorService.buscarPorId(id);
    }

    @GetMapping
    public List<Fornecedor> listarTodos() {
        return fornecedorService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {
        fornecedor.setIdFornecedor(id);
        fornecedorService.atualizar(fornecedor);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        fornecedorService.deletarPorId(id);
    }
}
