package br.com.project.service;

import br.com.project.dto.request.ProductRequestDTO;
import br.com.project.dto.response.*;
import br.com.project.model.Category;
import br.com.project.model.HistoricPriceProduct;
import br.com.project.model.Product;
import br.com.project.repository.CategoryRepository;
import br.com.project.repository.HistoricPriceProductRepository;
import br.com.project.repository.ProductRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final HistoricPriceProductRepository historicPriceProductRepository;
    private final CategoryRepository categoryRepository;
    private final MapperUtils mapperUtils;

    public ProductService(ProductRepository productRepository,
                          HistoricPriceProductRepository historicPriceProductRepository,
                          CategoryRepository categoryRepository,
                          MapperUtils mapperUtils) {
        this.productRepository = productRepository;
        this.historicPriceProductRepository = historicPriceProductRepository;
        this.categoryRepository = categoryRepository;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public ProductResponseDTO save(ProductRequestDTO requestDTO) {
        try {
            Product existing = productRepository.findByCodEa(requestDTO.getCodEa());
            if (existing != null) {
                throw new RuntimeException("Já existe um produto com este código EA");
            }

            Product product = new Product();
            product.setCodEa(requestDTO.getCodEa());
            product.setName(requestDTO.getName());
            product.setStockQuantity(requestDTO.getStockQuantity());
            product.setPrice(requestDTO.getPrice());
            product.setCategoryCode(requestDTO.getCategoryCode());

            Integer productId = productRepository.save(product);
            product.setIdProduct(productId);

            HistoricPriceProduct historic = new HistoricPriceProduct();
            historic.setProductId(productId);
            historic.setDate(LocalDate.now());
            historic.setPrice(requestDTO.getPrice());
            historicPriceProductRepository.save(historic);

            Category category = categoryRepository.findByCode(requestDTO.getCategoryCode())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            ProductResponseDTO.CategoryDTO categoryDTO = new ProductResponseDTO.CategoryDTO(
                    category.getCode(),
                    category.getName()
            );

            ProductResponseDTO response = mapperUtils.map(product, ProductResponseDTO.class);
            response.setCategory(categoryDTO);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public ProductResponseDTO update(Integer id, ProductRequestDTO requestDTO) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            Product existing = productRepository.findByCodEa(requestDTO.getCodEa());
            if (existing != null && !existing.getIdProduct().equals(id)) {
                throw new RuntimeException("Já existe um produto com este código EA");
            }

            product.setName(requestDTO.getName());
            product.setStockQuantity(requestDTO.getStockQuantity());
            product.setCodEa(requestDTO.getCodEa());
            product.setPrice(requestDTO.getPrice());
            product.setCategoryCode(requestDTO.getCategoryCode());

            productRepository.update(product);

            HistoricPriceProduct historic = new HistoricPriceProduct();
            historic.setProductId(id);
            historic.setDate(LocalDate.now());
            historic.setPrice(requestDTO.getPrice());

            historicPriceProductRepository.save(historic);

            Category category = categoryRepository.findByCode(requestDTO.getCategoryCode())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            ProductResponseDTO.CategoryDTO categoryDTO = new ProductResponseDTO.CategoryDTO(
                    category.getCode(),
                    category.getName()
            );

            ProductResponseDTO response = mapperUtils.map(product, ProductResponseDTO.class);
            response.setCategory(categoryDTO);
            return response;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ProductPurchaseCountResponseDTO> getMostPurchasedProducts() {
        return productRepository.getMostPurchasedProducts();
    }

    public List<ProductPurchaseCountResponseDTO> getLeastPurchasedProducts() {
        return productRepository.getLeastPurchasedProducts();
    }

    public int getTotalStockQuantity() {
        try {
            return productRepository.getTotalStockQuantity();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getTotalProductsCount() {
        try {
            return productRepository.getTotalProductsCount();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public double getAveragePrice() {
        try {
            return productRepository.getAveragePrice();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public double getTotalPrice() {
        try {
            return productRepository.getTotalPrice();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<CategoryProductCountDTO> getProductsCountByCategory() {
        try {
            return productRepository.getProductsCountByCategory();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<TopTierProductDTO> getMostExpensiveProducts() {
        try {
            return productRepository.getMostExpensiveProducts();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<LowStockProductDTO> getLowStockProducts() {
        try {
            return productRepository.getLowStockProducts();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<TopTierProductDTO> getLeastExpensiveProducts() {
        try {
            return productRepository.getLeastExpensiveProducts();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PriceHistoryResponseDTO> getHistoricoPreco(Integer produtoId) {
        return productRepository.findHistoricoPreco(produtoId);
    }

    public ProductResponseDTO findById(Integer id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ProductResponseDTO response = mapperUtils.map(product, ProductResponseDTO.class);

            Category category = categoryRepository.findByCode(response.getCategory().getCode())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            response.getCategory().setName(category.getName());

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ProductResponseDTO> findAll() {
        try {
            List<Product> products = productRepository.findAll();
            List<ProductResponseDTO> responseList = mapperUtils.mapList(products, ProductResponseDTO.class);

            for (ProductResponseDTO response : responseList) {
                Category category = categoryRepository.findByCode(response.getCategory().getCode())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
                response.getCategory().setName(category.getName());
            }

            return responseList;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void decreaseStock(Integer productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        int currentStock = product.getStockQuantity();
        if (currentStock < quantity) {
            throw new RuntimeException("Quantidade de estoque insuficiente");
        }

        product.setStockQuantity(currentStock - quantity);
        productRepository.update(product);
    }

    @Transactional
    public void increaseStock(Integer productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        int currentStock = product.getStockQuantity();
        product.setStockQuantity(currentStock + quantity);

        productRepository.update(product);
    }
}
