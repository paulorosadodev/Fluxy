package br.com.project.dto.response;

import java.sql.Date;

public record PurchaseResponseDTO(
        Integer number,
        Date date,
        Integer hour,
        Integer installments,
        String type,
        Integer productAmount,

        Integer fkProductId,
        Integer fkClientId,
        Integer fkOperationalIdEmployee
) {}
