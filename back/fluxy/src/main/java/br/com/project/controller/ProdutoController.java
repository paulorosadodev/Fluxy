package br.com.project.controller;

import br.com.project.dto.request.ProductRequestDTO;
import br.com.project.dto.response.ProductResponseDTO;
import br.com.project.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody ProductRequestDTO produtoRequestDTO) {
        produtoService.salvar(produtoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> buscarPorId(@PathVariable Integer id) {
        ProductResponseDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listarTodos() {
        List<ProductResponseDTO> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody ProductRequestDTO produtoRequestDTO) {
        produtoService.atualizar(id, produtoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        produtoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
