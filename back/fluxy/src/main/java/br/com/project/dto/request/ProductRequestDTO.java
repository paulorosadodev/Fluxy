package br.com.project.dto.request;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String codEa;
    private String name;
    private Integer stockQuantity;
    private Double price;
    private CategoryDTO category;
    private HistoricDTO historic;

    @Data
    public static class CategoryDTO {
        private String code;
    }

    @Data
    public static class HistoricDTO {
        private String date;   // Em String mesmo, parseamos depois
        private Double price;
    }
}
