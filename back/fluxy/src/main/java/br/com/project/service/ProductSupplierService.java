package br.com.project.service;

import br.com.project.dto.request.ProductSupplierRequestDTO;
import br.com.project.dto.response.ProductSupplierResponseDTO;
import br.com.project.model.ProductSupplier;
import br.com.project.repository.ProductSupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSupplierService {

    private final ProductSupplierRepository repository;

    public ProductSupplierService(ProductSupplierRepository repository) {
        this.repository = repository;
    }

    public void salvar(ProductSupplierRequestDTO dto) {
        if (dto.getSupplierId() == null || dto.getProductId() == null) {
            throw new IllegalArgumentException("Fornecedor ID e Produto ID são obrigatórios.");
        }
        ProductSupplier entity = dtoToEntity(dto);
        repository.save(entity);
    }

    public List<ProductSupplierResponseDTO> findAll() {
        List<ProductSupplier> entities = repository.findAll();
        return entities.stream()
                .map(this::entityToResponseDto)
                .toList();
    }

    public Optional<ProductSupplier> findBySupplierAndProduct(Integer fornecedorId, Integer produtoId) {
        return repository.findBySupplierAndProduct(fornecedorId, produtoId);
    }

    public void update(Integer fornecedorId, Integer produtoId, ProductSupplierRequestDTO dto) {
        Optional<ProductSupplier> existing = repository.findBySupplierAndProduct(fornecedorId, produtoId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para atualização.");
        }
        ProductSupplier entity = dtoToEntity(dto);
        entity.setSupplierId(fornecedorId);
        entity.setProductId(produtoId);
        repository.update(entity);
    }

    public void deleteBySupplierAndProduct(Integer fornecedorId, Integer produtoId) {
        Optional<ProductSupplier> existing = repository.findBySupplierAndProduct(fornecedorId, produtoId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para exclusão.");
        }
        repository.deleteById(fornecedorId, produtoId);
    }

    private ProductSupplierResponseDTO entityToResponseDto(ProductSupplier entity) {
        ProductSupplierResponseDTO dto = new ProductSupplierResponseDTO();
        dto.setSupplierId(entity.getSupplierId());
        dto.setProductId(entity.getProductId());
        dto.setProductAmount(entity.getProductAmount());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
