package br.com.project.dto.response;

public record ProdutoResponseDTO(
        Integer idProduto,
        Integer qtdEstoque,
        String codEa,
        Integer preco,
        String descricao,
        String nome
) {}
