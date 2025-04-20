package br.com.project.controller;

import br.com.project.dto.request.PessoaRequestDTO;
import br.com.project.dto.response.PessoaResponseDTO;
import br.com.project.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody PessoaRequestDTO pessoaRequestDTO) {
        pessoaService.salvar(pessoaRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPorId(@PathVariable Integer id) {
        PessoaResponseDTO responseDTO = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> listarTodas() {
        List<PessoaResponseDTO> pessoas = pessoaService.listarTodas();
        return ResponseEntity.ok(pessoas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        pessoaService.atualizar(id, pessoaRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pessoaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
