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

    public ProductSupplierResponseDTO salvar(ProductSupplierRequestDTO dto) {
        try {
            if (dto.getSupplier() == null || dto.getProduct() == null) {
                throw new IllegalArgumentException("Fornecedor e Produto são obrigatórios.");
            }

            Integer supplierId = supplierRepository.findSupplierIdByCnpj(dto.getSupplier());
            if (supplierId == null) {
                throw new RuntimeException("Fornecedor não encontrado com o CNPJ " + dto.getSupplier());
            }

            ProductSupplier productSupplier = new ProductSupplier();
            productSupplier.setSupplier(supplierId);
            productSupplier.setProduct(dto.getProduct());
            productSupplier.setProductAmount(dto.getProductAmount());
            productSupplier.setPrice(dto.getPrice());
            productSupplier.setDate(dto.getDate());

            repository.save(productSupplier);

            return new ProductSupplierResponseDTO(productSupplier.getSupplier(), productSupplier.getProduct(), productSupplier.getProductAmount(), productSupplier.getPrice());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a entrega: " + e.getMessage(), e);
        }
    }

    public List<ProductSupplierResponseDTO> findAll() {
        try {
            return repository.findAll().stream()
                    .map(this::entityToResponseDto)
                    .toList();
        } catch (Exception e){
            throw new RuntimeException("Erro ao listar a entrega: " + e.getMessage(), e);
        }
    }

    public void update(Integer fornecedorId, Integer produtoId, ProductSupplierRequestDTO dto) {
        Optional<ProductSupplier> existing = repository.findBySupplierAndProduct(fornecedorId, produtoId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para atualização.");
        }

        ProductSupplier productSupplier = new ProductSupplier();
        productSupplier.setSupplier(fornecedorId);
        productSupplier.setProduct(produtoId);
        productSupplier.setProductAmount(dto.getProductAmount());
        productSupplier.setPrice(dto.getPrice());
        productSupplier.setDate(dto.getDate());

        repository.update(productSupplier);
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
