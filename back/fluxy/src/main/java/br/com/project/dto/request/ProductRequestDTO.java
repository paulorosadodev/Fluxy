package br.com.project.dto.request;

import lombok.Data;

@Data
public class ProductRequestDTO {

    private Integer stockQuantity;
    private String codEa;
    private Double price;
    private String name;
    private String category;

    // Adicionados para relações:
    private String categoryCode;
    private Integer idSupplier;
    private Integer idHistoricalProductPrice;
}
