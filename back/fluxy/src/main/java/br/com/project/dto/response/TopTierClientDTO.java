package br.com.project.dto.response;

public class TopTierClientDTO {
    private String name;
    private Integer totalPurchases;

    public TopTierClientDTO(String name, Integer totalPurchases) {
        this.name = name;
        this.totalPurchases = totalPurchases;
    }

    public String getName() {
        return name;
    }

    public Integer getTotalPurchases() {
        return totalPurchases;
    }

}
