package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoHistorico {
    private Integer fkProdutoId;
    private Integer fkHistoricoPrecoProdutoId;
}
