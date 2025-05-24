package br.com.project.controller;

import br.com.project.dto.request.MonthYearRequest;
import br.com.project.dto.request.ProductSupplierRequestDTO;
import br.com.project.dto.response.ProductSupplierResponseDTO;
import br.com.project.dto.response.TopTierProductSupplyDTO;
import br.com.project.model.ProductSupplier;
import br.com.project.service.ProductSupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/supply")
public class ProductSupplierController {

    private final ProductSupplierService service;

    public ProductSupplierController(ProductSupplierService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ProductSupplierRequestDTO dto) {
        try {
            service.salvar(dto);
            return ResponseEntity.status(CREATED).build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao salvar entrega." + e.getMessage());
        }
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalDeliveries() {
        try {
            Integer total = service.getTotalDeliveries();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao obter total de entregas: " + e.getMessage());
        }
    }

    // é preciso enviar o mes e o ano pra esse funcionar
    @PostMapping("/monthly-total")
    public ResponseEntity<Integer> getDeliveriesByMonth(@RequestBody MonthYearRequest request) {
        try {
            int mes = request.getMonth();
            int ano = request.getYear();

            if (mes < 1 || mes > 12) {
                throw new ResponseStatusException(BAD_REQUEST, "O mês deve estar entre 1 e 12.");
            }

            Integer total = service.getDeliveriesByMonthAndYear(mes, ano);
            return ResponseEntity.ok(total);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao obter entregas: " + e.getMessage());
        }
    }

    @PostMapping("/monthly-total-cost")
    public ResponseEntity<Double> getTotalDeliveryCostByMonth(@RequestBody MonthYearRequest request) {
        try {
            int mes = request.getMonth();
            int ano = request.getYear();

            if (mes < 1 || mes > 12) {
                throw new ResponseStatusException(BAD_REQUEST, "O mês deve estar entre 1 e 12.");
            }

            Double total = service.getTotalDeliveryCostByMonthAndYear(mes, ano);
            return ResponseEntity.ok(total);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao obter custo total das entregas: " + e.getMessage());
        }
    }

    @GetMapping("/mais-caras")
    public ResponseEntity<List<TopTierProductSupplyDTO>> getMostExpensiveDeliveries() {
        try {
            List<TopTierProductSupplyDTO> entregas = service.getMostExpensiveDeliveries();
            return ResponseEntity.ok(entregas);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao buscar entregas mais caras", e);
        }
    }

    @GetMapping("/mais-barata")
    public ResponseEntity<List<TopTierProductSupplyDTO>> getLeastExpensiveDeliveries() {
        try {
            List<TopTierProductSupplyDTO> entregas = service.getLeastExpensiveDeliveries();
            return ResponseEntity.ok(entregas);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao buscar entregas mais baratas", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductSupplierResponseDTO>> findAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao listar entrega." + e.getMessage());
        }
    }

    @PutMapping("/{supplyId}")
    public ResponseEntity<Void> update(@PathVariable Integer supplyId, @RequestBody ProductSupplierRequestDTO dto) {
        try {
            service.update(supplyId, dto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao atualizar entrega.");
        }
    }

    @DeleteMapping("/{supplyId}")
    public ResponseEntity<Void> delete(@PathVariable Integer supplyId) {
        try {

            service.deleteById(supplyId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao deletar entrega.");
        }
    }
}
