package br.com.project.dto.response;

import java.sql.Date;

public record CompraResponseDTO(
        Integer numero,
        Date data,
        Integer hora,
        Integer parcelas,
        String tipo,
        Integer qtdProduto,
        Integer fkProdutoId,
        Integer fkClienteId,
        Integer fkOperacionalIdFuncionario
) {}
