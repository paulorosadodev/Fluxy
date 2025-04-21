package br.com.project.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoricPriceProductResponseDTO {
    private Integer idHistoricPriceProduct;
    private LocalDate date;
    private Double price;
}
