package br.com.project.dto.request;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String codEa;
    private String name;
    private Integer stockQuantity;
    private Double price;
    private String categoryCode;
}
