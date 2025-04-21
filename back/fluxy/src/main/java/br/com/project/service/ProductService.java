package br.com.project.service;

import br.com.project.dto.request.ProductRequestDTO;
import br.com.project.dto.response.ProductResponseDTO;
import br.com.project.model.Category;
import br.com.project.model.HistoricPriceProduct;
import br.com.project.model.Product;
import br.com.project.repository.CategoryRepository;
import br.com.project.repository.HistoricPriceProductRepository;
import br.com.project.repository.ProductRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

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

    public ProductResponseDTO save(ProductRequestDTO requestDTO) {
        // Criar o produto
        Product product = new Product();
        product.setCodEa(requestDTO.getCodEa());
        product.setName(requestDTO.getName());
        product.setStockQuantity(requestDTO.getStockQuantity());
        product.setPrice(requestDTO.getPrice());
        product.setCategoryCode(requestDTO.getCategory().getCode());

        Integer productId = productRepository.save(product);
        product.setIdProduct(productId);

        // Criar histórico de preço inicial
        HistoricPriceProduct historic = new HistoricPriceProduct();
        historic.setProductId(productId);
        historic.setDate(LocalDate.parse(requestDTO.getHistoric().getDate()));
        historic.setPrice(requestDTO.getHistoric().getPrice());
        historicPriceProductRepository.save(historic);

        // Buscar nome da categoria para retorno
        Category category = categoryRepository.findByCode(requestDTO.getCategory().getCode())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        ProductResponseDTO.CategoryDTO categoryDTO = new ProductResponseDTO.CategoryDTO(
                category.getCode(),
                category.getName()
        );

        ProductResponseDTO response = mapperUtils.map(product, ProductResponseDTO.class);
        response.setCategory(categoryDTO);
        return response;
    }

    public ProductResponseDTO update(Integer id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Atualizar informações do produto
        product.setName(requestDTO.getName());
        product.setStockQuantity(requestDTO.getStockQuantity());
        product.setCodEa(requestDTO.getCodEa());
        product.setPrice(requestDTO.getPrice());
        product.setCategoryCode(requestDTO.getCategory().getCode());

        productRepository.update(product);

        // Atualizar histórico de preço
        HistoricPriceProduct historic = new HistoricPriceProduct();
        historic.setProductId(id);
        historic.setDate(LocalDate.parse(requestDTO.getHistoric().getDate()));
        historic.setPrice(requestDTO.getHistoric().getPrice());

        historicPriceProductRepository.save(historic);

        // Buscar nome da categoria para retorno
        Category category = categoryRepository.findByCode(requestDTO.getCategory().getCode())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        ProductResponseDTO.CategoryDTO categoryDTO = new ProductResponseDTO.CategoryDTO(
                category.getCode(),
                category.getName()
        );

        ProductResponseDTO response = mapperUtils.map(product, ProductResponseDTO.class);
        response.setCategory(categoryDTO);
        return response;
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> responseList = mapperUtils.mapList(products, ProductResponseDTO.class);

        // Buscar e setar o nome da categoria para cada produto
        for (ProductResponseDTO response : responseList) {
            Category category = categoryRepository.findByCode(response.getCategory().getCode())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            response.getCategory().setName(category.getName());
        }

        return responseList;
    }

    public ProductResponseDTO findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ProductResponseDTO response = mapperUtils.map(product, ProductResponseDTO.class);

        Category category = categoryRepository.findByCode(response.getCategory().getCode())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        response.getCategory().setName(category.getName());

        return response;
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
