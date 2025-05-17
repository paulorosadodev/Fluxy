package br.com.project.dto.response;

public class LowStockProductDTO {
    private String name;
    private int stockQuantity;

    public LowStockProductDTO(String name, int stockQuantity) {
        this.name = name;
        this.stockQuantity = stockQuantity;
    }

    public String getName() {
        return name;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}

