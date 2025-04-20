package br.com.project.controller;

import br.com.project.dto.request.CompraRequestDTO;
import br.com.project.dto.response.CompraResponseDTO;
import br.com.project.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody CompraRequestDTO compraRequestDTO) {
        compraService.salvar(compraRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{numero}")
    public ResponseEntity<CompraResponseDTO> buscarPorNumero(@PathVariable Integer numero) {
        CompraResponseDTO responseDTO = compraService.buscarPorNumero(numero);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listarTodas() {
        List<CompraResponseDTO> compras = compraService.listarTodas();
        return ResponseEntity.ok(compras);
    }

    @PutMapping("/{numero}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer numero, @RequestBody CompraRequestDTO compraRequestDTO) {
        compraService.atualizar(numero, compraRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletar(@PathVariable Integer numero) {
        compraService.deletar(numero);
        return ResponseEntity.ok().build();
    }
}
