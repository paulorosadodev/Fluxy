package br.com.project.dto.response;

public class CategoryProductCountDTO {
    private String categoryCode;
    private int productCount;

    public CategoryProductCountDTO(String categoryCode, int productCount) {
        this.categoryCode = categoryCode;
        this.productCount = productCount;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public int getProductCount() {
        return productCount;
    }
}
