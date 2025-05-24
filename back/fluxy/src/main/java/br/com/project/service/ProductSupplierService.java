package br.com.project.service;

import br.com.project.dto.request.ProductSupplierRequestDTO;
import br.com.project.dto.response.ProductSupplierResponseDTO;
import br.com.project.dto.response.TopTierProductSupplyDTO;
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
            repository.increaseStock(Integer.valueOf(dto.getProduct()), dto.getProductAmount());

            return new ProductSupplierResponseDTO(productSupplier.getSupplier(), productSupplier.getProduct(), productSupplier.getProductAmount(), productSupplier.getPrice());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Integer getTotalDeliveries() {
        try {
            return repository.countTotalDeliveries();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Integer getDeliveriesByMonthAndYear(int month, int year) {
        try {
            return repository.countDeliveriesByMonthAndYear(month, year);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Double getTotalDeliveryCostByMonthAndYear(int month, int year) {
        try {
            return repository.sumDeliveryCostsByMonthAndYear(month, year);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Double getAverageDeliveryCostByMonthAndYear(int month, int year) {
        return productSupplierRepository.averageDeliveryCostByMonthAndYear(month, year);
    }

    public List<TopTierProductSupplyDTO> getMostExpensiveDeliveries() {
        try {
            return repository.findMostExpensiveDeliveries();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar entregas mais caras: " + e.getMessage(), e);
        }
    }

    public List<TopTierProductSupplyDTO> getLeastExpensiveDeliveries() {
        try {
            return repository.findLeastExpensiveDeliveries();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar entregas mais baratas: " + e.getMessage(), e);
        }
    }

    public List<ProductSupplierResponseDTO> findAll() {
        try {
            return repository.findAll().stream().map(this::entityToResponseDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void update(Integer supplyId, ProductSupplierRequestDTO dto) {
        Optional<ProductSupplier> existing = repository.findById(supplyId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para atualização.");
        }

        ProductSupplier productSupplier = existing.get();
        int previousAmount = productSupplier.getProductAmount();
        int previousProduct = productSupplier.getProduct();

        productSupplier.setSupplier(supplierRepository.findSupplierIdByCnpj(dto.getSupplier()));
        productSupplier.setProduct(Integer.valueOf(dto.getProduct()));
        productSupplier.setProductAmount(dto.getProductAmount());
        productSupplier.setPrice(dto.getPrice());
        productSupplier.setDate(dto.getDate());

        repository.update(productSupplier);

        int newProduct = productSupplier.getProduct();
        int newAmount = dto.getProductAmount();

        if (previousProduct != newProduct) {
            repository.decreaseStock(previousProduct, previousAmount);
            repository.increaseStock(newProduct, newAmount);
        } else {
            int difference = newAmount - previousAmount;
            if (difference > 0) {
                repository.increaseStock(newProduct, difference);
            } else if (difference < 0) {
                repository.decreaseStock(newProduct, -difference);
            }
        }
    }

    public void deleteById(Integer supplyId) {
        if (repository.findById(supplyId).isEmpty()) {
            throw new IllegalArgumentException("ProductSupplier não encontrado para exclusão.");
        }
        Integer qtd = repository.findStockBySupplyId(supplyId);
        Integer productId = repository.findProductIdByEntregaId(supplyId);
        repository.decreaseStock(productId, qtd);
        repository.deleteById(supplyId);
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
