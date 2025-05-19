package br.com.project.dto.response;

public class TopTierProductDTO {
    private String name;
    private double price;

    public TopTierProductDTO(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
