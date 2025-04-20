package br.com.project.controller;

import br.com.project.dto.request.PersonRequestDTO;
import br.com.project.dto.response.PersonResponseDTO;
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
    public ResponseEntity<Void> salvar(@RequestBody PersonRequestDTO pessoaRequestDTO) {
        pessoaService.save(pessoaRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> buscarPorId(@PathVariable Integer id) {
        PersonResponseDTO responseDTO = pessoaService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> listarTodas() {
        List<PersonResponseDTO> pessoas = pessoaService.listAll();
        return ResponseEntity.ok(pessoas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody PersonRequestDTO pessoaRequestDTO) {
        pessoaService.update(id, pessoaRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pessoaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
