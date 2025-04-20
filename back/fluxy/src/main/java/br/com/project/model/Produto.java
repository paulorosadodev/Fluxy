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
    private Double preco;
    private String nome;
    private String codigoCategoria;
}
