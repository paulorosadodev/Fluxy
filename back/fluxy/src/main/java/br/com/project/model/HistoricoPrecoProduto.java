package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoPrecoProduto {
    private Integer idHistoricoPrecoProduto;
    private Date data;
    private Integer preco;
}
