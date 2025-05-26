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
    public ResponseEntity<?> save(@RequestBody HistoricPriceProductRequestDTO requestDTO) {
        try {
            HistoricPriceProductResponseDTO response = historicPriceProductService.save(requestDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar histórico de preço: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<HistoricPriceProductResponseDTO> lista = historicPriceProductService.findAll();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao listar históricos de preço: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            HistoricPriceProductResponseDTO response = historicPriceProductService.findById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar histórico de preço: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody HistoricPriceProductRequestDTO requestDTO) {
        try {
            HistoricPriceProductResponseDTO response = historicPriceProductService.update(id, requestDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar histórico de preço: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            historicPriceProductService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao deletar histórico de preço: " + e.getMessage());
        }
    }
}
