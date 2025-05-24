package br.com.project.dto.response;

import lombok.Data;

@Data
public class TopTierProductSupplyDTO {

    private String productName;
    private int amount;
    private double price;

    public TopTierProductSupplyDTO(String productName, int amount, double price) {
        this.productName = productName;
        this.amount = amount;
        this.price = price;
    }
}
