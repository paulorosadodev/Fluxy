package br.com.project.controller;

import br.com.project.dto.request.SupplierRequestDTO;
import br.com.project.dto.response.SupplierResponseDTO;
import br.com.project.service.FornecedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody SupplierRequestDTO fornecedorRequestDTO) {
        fornecedorService.salvar(fornecedorRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> buscarPorId(@PathVariable Integer id) {
        SupplierResponseDTO responseDTO = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> listarTodos() {
        List<SupplierResponseDTO> fornecedores = fornecedorService.listarTodos();
        return ResponseEntity.ok(fornecedores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody SupplierRequestDTO fornecedorRequestDTO) {
        fornecedorService.atualizar(id, fornecedorRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        fornecedorService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
