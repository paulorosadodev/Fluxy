package br.com.project.dto.response;

import lombok.Data;

@Data
public class CategoryProductCountDTO {
    private String categoryCode;
    private int productCount;

    public CategoryProductCountDTO(String categoryCode, int productCount) {
        this.categoryCode = categoryCode;
        this.productCount = productCount;
    }
}
