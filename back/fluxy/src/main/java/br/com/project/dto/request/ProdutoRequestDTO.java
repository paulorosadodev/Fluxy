package br.com.project.dto.request;

import lombok.Data;

@Data
public class ProdutoRequestDTO {

    private Integer qtdEstoque;
    private String codEa;
    private Double preco;
    private String nome;

    // Adicionados para relações:
    private String codigoCategoria;
    private Integer idFornecedor;
    private Integer idHistoricoPrecoProduto;
}
