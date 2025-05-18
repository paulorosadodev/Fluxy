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
    private final ProductSupplierRepository productSupplierRepository;

    public ProductSupplierService(ProductSupplierRepository repository, SupplierRepository supplierRepository, ProductSupplierRepository productSupplierRepository) {
        this.repository = repository;
        this.supplierRepository = supplierRepository;
        this.productSupplierRepository = productSupplierRepository;
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
            productSupplier.setProduct(Integer.valueOf(dto.getProduct()));
            productSupplier.setProductAmount(dto.getProductAmount());
            productSupplier.setPrice(dto.getPrice());
            productSupplier.setDate(dto.getDate());

            repository.save(productSupplier);
            increaseProductStock(Integer.valueOf(dto.getProduct()), dto.getProductAmount());

            return new ProductSupplierResponseDTO(productSupplier.getSupplier(), productSupplier.getProduct(), productSupplier.getProductAmount(), productSupplier.getPrice());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a entrega: " + e.getMessage(), e);
        }
    }

    public void increaseProductStock(Integer productId, Integer quantity) {
        repository.updateStock(productId, quantity);
    }

    public List<ProductSupplierResponseDTO> findAll() {
        try {
            return repository.findAll().stream().map(this::entityToResponseDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar as entregas: " + e.getMessage(), e);
        }
    }

    public void update(Integer supplyId, ProductSupplierRequestDTO dto) {
        Optional<ProductSupplier> existing = repository.findById(supplyId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para atualização.");
        }

        ProductSupplier productSupplier = existing.get();
        productSupplier.setSupplier(supplierRepository.findSupplierIdByCnpj(dto.getSupplier()));
        productSupplier.setProduct(Integer.valueOf(dto.getProduct()));
        productSupplier.setProductAmount(dto.getProductAmount());
        productSupplier.setPrice(dto.getPrice());
        productSupplier.setDate(dto.getDate());

        repository.update(productSupplier);
    }

    public void deleteById(Integer supplyId) {
        if (repository.findById(supplyId).isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para exclusão.");
        }
        repository.deleteById(supplyId);
        // atualizar qtd de produto na deleção
    }

    public void decreaseProductStock(Integer productId, Integer quantity) {
        repository.decreaseStock(productId, quantity);
    }

    private ProductSupplierResponseDTO entityToResponseDto(ProductSupplier entity) {
        return new ProductSupplierResponseDTO(
                entity.getId(),
                Integer.toString(entity.getSupplier()),
                Integer.toString(entity.getProduct()),
                entity.getProductAmount(),
                entity.getPrice(),
                entity.getDate()
        );
    }
}
