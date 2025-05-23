package br.com.project.dto.response;

import lombok.Data;

@Data
public class TopTierClientDTO {
    private String name;
    private Integer totalPurchases;

    public TopTierClientDTO(String name, Integer totalPurchases) {
        this.name = name;
        this.totalPurchases = totalPurchases;
    }

}
