package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoPrecoProduto {

    private Integer idHistoricoPrecoProduto;
    private LocalDate data; // <-- Corrige aqui para LocalDate
    private Double preco;
}
