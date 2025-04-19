package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private Integer idProduto;
    private Integer qtdEstoque;
    private String codEa;
    private Integer preco;
    private String descricao;
    private String nome;
}
