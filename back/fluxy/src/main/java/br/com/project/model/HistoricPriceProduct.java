package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricPriceProduct {
    private Integer idHistoricPriceProduct;
    private LocalDate date;
    private Double price;
}
