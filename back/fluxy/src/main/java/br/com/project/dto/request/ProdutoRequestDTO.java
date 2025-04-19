package br.com.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {
    private Integer qtdEstoque;
    private String codEa;
    private Integer preco;
    private String descricao;
    private String nome;
}
