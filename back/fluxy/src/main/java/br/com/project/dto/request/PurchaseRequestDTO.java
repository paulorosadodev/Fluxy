package br.com.project.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record PurchaseRequestDTO(
        LocalDate date,
        LocalTime time,
        Integer total,
        Integer installments,
        String type,
        Integer productAmount,

        Integer fkProductId,
        Integer fkClientId,
        Integer fkOperationalIdEmployee
) {}
