package br.com.project.dto.response;

import lombok.Data;

@Data
public class TopTierProductDTO {
    private String name;
    private double price;

    public TopTierProductDTO(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
