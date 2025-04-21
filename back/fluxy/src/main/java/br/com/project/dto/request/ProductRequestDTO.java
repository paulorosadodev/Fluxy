package br.com.project.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductRequestDTO {
    private Integer stockQuantity;
    private String codEa;
    private Double price;
    private String name;
    private String categoryCode;
    private Integer supplierId;
    private LocalDate priceDate;

}
