package br.com.project.controller;

import br.com.project.dto.request.ProductRequestDTO;
import br.com.project.dto.response.ProductResponseDTO;
import br.com.project.service.ProductService;
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
    public ProductResponseDTO save(@RequestBody ProductRequestDTO requestDTO) {
        return productService.save(requestDTO);
    }

    @GetMapping
    public List<ProductResponseDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Integer id, @RequestBody ProductRequestDTO requestDTO) {
        return productService.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
}
