package br.com.project.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoricPriceProductRequestDTO {
    private int productId;
    private LocalDate date;
    private Double price;
}
