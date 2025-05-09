package br.com.project.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record PurchaseResponseDTO(
        Integer number,
        LocalDate date,
        LocalTime time,
        Integer installments,
        String type,
        Integer productAmount,

        Integer fkProductId,
        Integer fkClientId,
        Integer fkOperationalIdEmployee
) {}
