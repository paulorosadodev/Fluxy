package br.com.project.controller;

import br.com.project.dto.request.CategoriaRequestDTO;
import br.com.project.dto.response.CategoriaResponseDTO;
import br.com.project.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.ok(categoriaService.salvar(categoriaRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(categoriaService.listarTodos());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(categoriaService.buscarPorCodigo(codigo));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable String codigo, @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.ok(categoriaService.atualizar(codigo, categoriaRequestDTO));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable String codigo) {
        categoriaService.deletar(codigo);
        return ResponseEntity.ok().build();
    }
}
