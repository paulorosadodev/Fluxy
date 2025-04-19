package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {
    private Integer idProduto;
    private Integer qtdEstoque;
    private String codEa;
    private Integer preco;
    private String descricao;
    private String nome;
}
