package br.com.project.controller;

import br.com.project.dto.request.LegalClientRequestDTO;
import br.com.project.dto.response.LegalClientResponseDTO;
import br.com.project.service.LegalClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/legal-clients")
public class LegalClientController {

    private final LegalClientService service;

    public LegalClientController(LegalClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody LegalClientRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LegalClientResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<LegalClientResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody LegalClientRequestDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
