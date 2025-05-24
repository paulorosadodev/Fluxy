package br.com.project.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class TopTierClientDTO {
    private String name;
    private Integer totalPurchases;
    private Date dateOfPurchase;

    public TopTierClientDTO(String name, Integer totalPurchases, Date dateOfPurchase) {
        this.name = name;
        this.totalPurchases = totalPurchases;
        this.dateOfPurchase = dateOfPurchase;
    }

    public TopTierClientDTO(String name, Integer totalPurchases) {
        this.name = name;
        this.totalPurchases = totalPurchases;
    }

}
