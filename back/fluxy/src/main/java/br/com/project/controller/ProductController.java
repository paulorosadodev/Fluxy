package br.com.project.controller;

import br.com.project.dto.request.ProductIdRequestDTO;
import br.com.project.dto.request.ProductRequestDTO;
import br.com.project.dto.response.*;
import br.com.project.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductRequestDTO requestDTO) {
        try {
            ProductResponseDTO response = productService.save(requestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao salvar produto: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar produto: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ProductResponseDTO> products = productService.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao listar produtos: " + e.getMessage());
        }
    }

    @GetMapping("/total-stock")
    public ResponseEntity<?> getTotalStockQuantity() {
        try {
            int totalStock = productService.getTotalStockQuantity();
            return ResponseEntity.ok(totalStock);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter quantidade total em estoque: " + e.getMessage());
        }
    }

    @GetMapping("/total-products")
    public ResponseEntity<?> getTotalProductsCount() {
        try {
            int totalProducts = productService.getTotalProductsCount();
            return ResponseEntity.ok(totalProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter total de produtos: " + e.getMessage());
        }
    }

    @GetMapping("/average-price")
    public ResponseEntity<?> getAveragePrice() {
        try {
            double totalProducts = productService.getAveragePrice();
            return ResponseEntity.ok(totalProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter preço médio: " + e.getMessage());
        }
    }

    @GetMapping("/total-price")
    public ResponseEntity<?> getTotalPrice() {
        try {
            double totalProducts = productService.getTotalPrice();
            return ResponseEntity.ok(totalProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter preço total do estoque: " + e.getMessage());
        }
    }

    @GetMapping("/products-count-by-category")
    public ResponseEntity<?> getProductsCountByCategory() {
        try {
            List<CategoryProductCountDTO> totalProducts = productService.getProductsCountByCategory();
            return ResponseEntity.ok(totalProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter quantidade de produtos por " +
                    "categoria: " + e.getMessage());
        }
    }

    @GetMapping("/most-bought-products")
    public ResponseEntity<List<ProductPurchaseCountResponseDTO>> getMostPurchasedProducts() {
        List<ProductPurchaseCountResponseDTO> produtos = productService.getMostPurchasedProducts();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/least-bought-products")
    public ResponseEntity<List<ProductPurchaseCountResponseDTO>> getLeastPurchasedProducts() {
        List<ProductPurchaseCountResponseDTO> produtos = productService.getLeastPurchasedProducts();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/most-expensive-products")
    public ResponseEntity<?> getMostExpensiveProducts() {
        try {
            List<TopTierProductDTO> totalProducts = productService.getMostExpensiveProducts();
            return ResponseEntity.ok(totalProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter produtos mais caros: " + e.getMessage());
        }
    }

    @GetMapping("/low-stock-products")
    public ResponseEntity<?> getLowStockProducts() {
        try {
            List<LowStockProductDTO> lowStockProducts = productService.getLowStockProducts();
            return ResponseEntity.ok(lowStockProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter produtos com baixo estoque: " + e.getMessage());
        }
    }

    @GetMapping("/least-expensive-products")
    public ResponseEntity<?> getLeastExpensiveProducts() {
        try {
            List<TopTierProductDTO> totalProducts = productService.getLeastExpensiveProducts();
            return ResponseEntity.ok(totalProducts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter produtos mais baratos: " + e.getMessage());
        }
    }

    @PostMapping("/price-history")
    public ResponseEntity<List<PriceHistoryResponseDTO>> getHistoricoPreco(@RequestBody ProductIdRequestDTO request) {
        List<PriceHistoryResponseDTO> historico = productService.getHistoricoPreco(request.getProductId());
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            ProductResponseDTO response = productService.findById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar produto: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProductRequestDTO requestDTO) {
        try {
            ProductResponseDTO response = productService.update(id, requestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar produto: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao deletar produto: " + e.getMessage());
        }
    }
}
