package br.com.project.controller;

import br.com.project.dto.request.MonthYearRequest;
import br.com.project.dto.request.PurchaseRequestDTO;
import br.com.project.dto.response.PaymentTypeCountResponseDTO;
import br.com.project.dto.response.PurchaseCountByMonthAndYearResponseDTO;
import br.com.project.dto.response.PurchaseResponseDTO;
import br.com.project.dto.response.PurchaseTotalCountResponseDTO;
import br.com.project.model.Purchase;
import br.com.project.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService service;
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService service, PurchaseService purchaseService) {
        this.service = service;
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PurchaseRequestDTO dto) {
        try {
            PurchaseResponseDTO responseDTO = service.save(dto);
            URI location = URI.create("/purchases/" + responseDTO.getNumber());
            return ResponseEntity.created(location).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro ao salvar compra: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao salvar compra: " + e.getMessage());
        }
    }

    @PostMapping("/monthly-purchase-costs")
    public ResponseEntity<Double> getTotalPurchaseCostByMonth(@RequestBody MonthYearRequest request) {
        try {
            int mes = request.getMonth();
            int ano = request.getYear();

            if (mes < 1 || mes > 12) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O mês deve estar entre 1 e 12.");
            }

            Double total = service.getTotalPurchaseCostByMonthAndYear(mes, ano);
            return ResponseEntity.ok(total);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter custo total das compras: " + e.getMessage());
        }
    }

    @GetMapping("/total")
    public ResponseEntity<PurchaseTotalCountResponseDTO> getTotalCompras() {
        int total = purchaseService.countAllPurchases();
        return ResponseEntity.ok(new PurchaseTotalCountResponseDTO(total));
    }

    @GetMapping("/total-costs")
    public ResponseEntity<Double> getTotalPurchaseCosts() {
        Double total = service.getTotalPurchaseCost();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/payment-type-count")
    public ResponseEntity<List<PaymentTypeCountResponseDTO>> getPaymentTypeCounts() {
        List<PaymentTypeCountResponseDTO> result = purchaseService.getPaymentTypeCounts();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/by-month-year")
    public ResponseEntity<?> findByMonthAndYear(@RequestBody MonthYearRequest request) {
        try {
            int mes = request.getMonth();
            int ano = request.getYear();

            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest().body("O mês deve estar entre 1 e 12.");
            }

            PurchaseCountByMonthAndYearResponseDTO response = service.getPurchaseCountByMonthAndYear(mes, ano);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar a quantidade de compras por mês e ano: " + e.getMessage());
        }
    }

    @GetMapping("/most-expensive-purchases")
    public ResponseEntity<List<Purchase>> getPurchasesOrderedByCostDesc() {
        List<Purchase> purchases = purchaseService.getAllPurchasesOrderedByCostDesc();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/least-expensive-purchases")
    public ResponseEntity<List<Purchase>> getPurchasesOrderedByCostAsc() {
        List<Purchase> purchases = purchaseService.getAllPurchasesOrderedByCostAsc();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> findByNumber(@PathVariable Integer number) {
        try {
            PurchaseResponseDTO responseDTO = service.findByNumber(number);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar compra: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<PurchaseResponseDTO> responseList = service.findAll();
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar compras: " + e.getMessage());
        }
    }

    @PutMapping("/{number}")
    public ResponseEntity<?> update(@PathVariable Integer number, @RequestBody PurchaseRequestDTO dto) {
        try {
            service.update(number, dto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar compra: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao atualizar compra: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao atualizar compra: " + e.getMessage());
        }
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteByNumber(@PathVariable Integer number) {
        try {
            service.deleteByNumber(number);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar compra: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao deletar compra: " + e.getMessage());
        }
    }
}
