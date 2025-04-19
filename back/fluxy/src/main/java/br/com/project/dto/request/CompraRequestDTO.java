package br.com.project.dto.request;

import java.sql.Date;

public record CompraRequestDTO(
        Date data,
        Integer hora,
        Integer parcelas,
        String tipo,
        Integer qtdProduto,
        Integer fkProdutoId,
        Integer fkClienteId,
        Integer fkOperacionalIdFuncionario
) {}
