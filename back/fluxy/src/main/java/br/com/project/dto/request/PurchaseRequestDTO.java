package br.com.project.dto.request;

import java.sql.Date;

public record PurchaseRequestDTO(
        Date date,
        Integer total,
        Integer installments,
        String type,
        Integer productAmount,

        Integer fkProductId,
        Integer fkClientId,
        Integer fkOperationalIdFunctionary
) {}
