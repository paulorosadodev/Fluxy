package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Integer idProduct;
    private Integer stockQuantity;
    private String codEa;
    private Double price;
    private String name;
    private String FKCategory; // FK adicionada
}
