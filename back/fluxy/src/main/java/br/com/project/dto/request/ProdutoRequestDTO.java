package br.com.project.dto.request;

public record ProdutoRequestDTO(
        Integer qtdEstoque,
        String codEa,
        Integer preco,
        String descricao,
        String nome
) {}
