package br.com.project.service;

import br.com.project.dto.request.ProductSupplierRequestDTO;
import br.com.project.dto.response.ProductSupplierResponseDTO;
import br.com.project.model.ProductSupplier;
import br.com.project.repository.ProductSupplierRepository;
import br.com.project.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSupplierService {

    private final ProductSupplierRepository repository;
    private final SupplierRepository supplierRepository;

    public ProductSupplierService(ProductSupplierRepository repository, SupplierRepository supplierRepository) {
        this.repository = repository;
        this.supplierRepository = supplierRepository;
    }

    public void salvar(ProductSupplierRequestDTO dto) {
        if (dto.getSupplier() == null || dto.getProduct() == null) {
            throw new IllegalArgumentException("Fornecedor ID e Produto ID são obrigatórios.");
        }

        ProductSupplier entity = new ProductSupplier(
                supplierRepository.findSupplierIdByCnpj(dto.getSupplier()),
                dto.getProduct(),
                dto.getProductAmount(),
                dto.getPrice(),
                dto.getDate()
        );

        repository.save(entity);
    }

    public List<ProductSupplierResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::entityToResponseDto)
                .toList();
    }

    public void update(Integer fornecedorId, Integer produtoId, ProductSupplierRequestDTO dto) {
        Optional<ProductSupplier> existing = repository.findBySupplierAndProduct(fornecedorId, produtoId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para atualização.");
        }

        ProductSupplier entity = new ProductSupplier(
                fornecedorId,
                produtoId,
                dto.getProductAmount(),
                dto.getPrice(),
                dto.getDate()
        );

        repository.update(entity);
    }

    public void deleteBySupplierAndProduct(Integer fornecedorId, Integer produtoId) {
        if (repository.findBySupplierAndProduct(fornecedorId, produtoId).isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para exclusão.");
        }
        repository.deleteById(fornecedorId, produtoId);
    }

    private ProductSupplierResponseDTO entityToResponseDto(ProductSupplier entity) {
        return new ProductSupplierResponseDTO(
                entity.getId(),
                Integer.toString(entity.getSupplier()),
                entity.getProduct(),
                entity.getProductAmount(),
                entity.getPrice(),
                entity.getDate()
        );
    }
}
