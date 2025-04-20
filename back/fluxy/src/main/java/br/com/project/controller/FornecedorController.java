package br.com.project.controller;

import br.com.project.dto.request.FornecedorRequestDTO;
import br.com.project.dto.response.FornecedorResponseDTO;
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
    public ResponseEntity<Void> salvar(@RequestBody FornecedorRequestDTO fornecedorRequestDTO) {
        fornecedorService.salvar(fornecedorRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(@PathVariable Integer id) {
        FornecedorResponseDTO responseDTO = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listarTodos() {
        List<FornecedorResponseDTO> fornecedores = fornecedorService.listarTodos();
        return ResponseEntity.ok(fornecedores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody FornecedorRequestDTO fornecedorRequestDTO) {
        fornecedorService.atualizar(id, fornecedorRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        fornecedorService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
