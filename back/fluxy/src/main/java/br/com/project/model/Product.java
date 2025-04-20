package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer idProduct;
    private Integer stockQuantity;
    private String codEa;
    private Double price;
    private String name;
    private String category;
}
