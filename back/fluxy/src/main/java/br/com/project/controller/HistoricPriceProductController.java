package br.com.project.controller;

import br.com.project.dto.request.HistoricPriceProductRequestDTO;
import br.com.project.dto.response.HistoricPriceProductResponseDTO;
import br.com.project.service.HistoricPriceProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historic-price-products")
public class HistoricPriceProductController {

    private final HistoricPriceProductService historicPriceProductService;

    public HistoricPriceProductController(HistoricPriceProductService historicPriceProductService) {
        this.historicPriceProductService = historicPriceProductService;
    }

    @PostMapping
    public ResponseEntity<HistoricPriceProductResponseDTO> save(@RequestBody HistoricPriceProductRequestDTO requestDTO) {
        return ResponseEntity.ok(historicPriceProductService.save(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<HistoricPriceProductResponseDTO>> findAll() {
        return ResponseEntity.ok(historicPriceProductService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricPriceProductResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(historicPriceProductService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricPriceProductResponseDTO> update(@PathVariable Integer id, @RequestBody HistoricPriceProductRequestDTO requestDTO) {
        return ResponseEntity.ok(historicPriceProductService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        historicPriceProductService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
