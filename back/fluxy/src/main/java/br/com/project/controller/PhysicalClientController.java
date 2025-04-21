package br.com.project.controller;

import br.com.project.dto.request.PhysicalClientRequestDTO;
import br.com.project.dto.response.PhysicalClientResponseDTO;
import br.com.project.service.PhysicalClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/physical-clients")
public class PhysicalClientController {

    private final PhysicalClientService service;

    public PhysicalClientController(PhysicalClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody PhysicalClientRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalClientResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PhysicalClientResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody PhysicalClientRequestDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
