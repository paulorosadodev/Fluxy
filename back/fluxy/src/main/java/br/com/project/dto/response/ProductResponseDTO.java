package br.com.project.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDTO {
    private Integer idProduct;
    private Integer stockQuantity;
    private String codEa;
    private Double price;
    private String name;
    private String categoryCode;
    private List<Integer> supplierIds;
    private List<PriceHistoryDTO> priceHistories;

    @Data
    public static class PriceHistoryDTO {
        private String date;
        private Double price;
    }
}
