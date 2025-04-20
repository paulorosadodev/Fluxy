package br.com.project.controller;

import br.com.project.dto.request.JuridicalRequestDTO;
import br.com.project.dto.response.JuridicalResponseDTO;
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
    public ResponseEntity<Void> salvar(@RequestBody JuridicalRequestDTO juridicoRequestDTO) {
        juridicoService.salvar(juridicoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuridicalResponseDTO> buscarPorId(@PathVariable Integer id) {
        JuridicalResponseDTO responseDTO = juridicoService.buscarPorFkClienteId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<JuridicalResponseDTO>> listarTodos() {
        List<JuridicalResponseDTO> juridicos = juridicoService.listarTodos();
        return ResponseEntity.ok(juridicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody JuridicalRequestDTO juridicoRequestDTO) {
        juridicoService.atualizar(id, juridicoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        juridicoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
