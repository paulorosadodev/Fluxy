package br.com.project.controller;

import br.com.project.dto.request.JuridicoRequestDTO;
import br.com.project.dto.response.JuridicoResponseDTO;
import br.com.project.service.JuridicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/juridicos")
public class JuridicoController {

    private final JuridicoService juridicoService;

    public JuridicoController(JuridicoService juridicoService) {
        this.juridicoService = juridicoService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody JuridicoRequestDTO juridicoRequestDTO) {
        juridicoService.salvar(juridicoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuridicoResponseDTO> buscarPorId(@PathVariable Integer id) {
        JuridicoResponseDTO responseDTO = juridicoService.buscarPorFkClienteId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<JuridicoResponseDTO>> listarTodos() {
        List<JuridicoResponseDTO> juridicos = juridicoService.listarTodos();
        return ResponseEntity.ok(juridicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody JuridicoRequestDTO juridicoRequestDTO) {
        juridicoService.atualizar(id, juridicoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        juridicoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
