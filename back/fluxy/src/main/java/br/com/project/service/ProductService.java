package br.com.project.service;

import br.com.project.dto.request.ProductRequestDTO;
import br.com.project.dto.response.ProductResponseDTO;
import br.com.project.model.HistoricPriceProduct;
import br.com.project.model.Product;
import br.com.project.model.ProductCategory;
import br.com.project.model.ProductPriceHistoryLink;
import br.com.project.model.ProductSupplier;
import br.com.project.repository.HistoricPriceProductRepository;
import br.com.project.repository.ProductCategoryRepository;
import br.com.project.repository.ProductHistoryRepository;
import br.com.project.repository.ProductRepository;
import br.com.project.repository.ProductSupplierRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductSupplierRepository productSupplierRepository;
    private final HistoricPriceProductRepository historicPriceProductRepository;
    private final ProductHistoryRepository productHistoryRepository;
    private final MapperUtils mapperUtils;

    public ProductService(ProductRepository productRepository,
                          ProductCategoryRepository productCategoryRepository,
                          ProductSupplierRepository productSupplierRepository,
                          HistoricPriceProductRepository historicPriceProductRepository,
                          ProductHistoryRepository productHistoryRepository,
                          MapperUtils mapperUtils) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productSupplierRepository = productSupplierRepository;
        this.historicPriceProductRepository = historicPriceProductRepository;
        this.productHistoryRepository = productHistoryRepository;
        this.mapperUtils = mapperUtils;
    }

    public ProductResponseDTO save(ProductRequestDTO requestDTO) {
        Product product = mapperUtils.map(requestDTO, Product.class);

        // 1. Salva o produto
        Integer productId = productRepository.save(product);

        // 2. Salva a relação produto-categoria
        productCategoryRepository.save(new ProductCategory(productId, requestDTO.getCategoryCode()));

        // 3. Se tiver fornecedor, salva a relação produto-fornecedor
        if (requestDTO.getSupplierId() != null) {
            productSupplierRepository.save(new ProductSupplier(requestDTO.getSupplierId(), productId));
        }

        // 4. Salva histórico de preço (usando o preço atual e data atual)
        HistoricPriceProduct historic = new HistoricPriceProduct();
        historic.setDate(LocalDate.now());
        historic.setPrice(product.getPrice());

        Integer historyId = historicPriceProductRepository.save(historic);

        // 5. Cria o vínculo produto-histórico
        productHistoryRepository.save(new ProductPriceHistoryLink(productId, historyId));

        product.setIdProduct(productId);
        return mapperUtils.map(product, ProductResponseDTO.class);
    }

    public List<ProductResponseDTO> findAll() {
        return mapperUtils.mapList(productRepository.findAll(), ProductResponseDTO.class);
    }

    public ProductResponseDTO findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapperUtils.map(product, ProductResponseDTO.class);
    }

    public ProductResponseDTO update(Integer id, ProductRequestDTO requestDTO) {
        Product product = mapperUtils.map(requestDTO, Product.class);
        product.setIdProduct(id);
        productRepository.update(product);

        productCategoryRepository.deleteByProductId(id);
        productCategoryRepository.save(new ProductCategory(id, requestDTO.getCategoryCode()));

        productSupplierRepository.deleteByProductId(id);
        if (requestDTO.getSupplierId() != null) {
            productSupplierRepository.save(new ProductSupplier(requestDTO.getSupplierId(), id));
        }

        return mapperUtils.map(product, ProductResponseDTO.class);
    }

    public void delete(Integer id) {
        productCategoryRepository.deleteByProductId(id);
        productSupplierRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }
}
