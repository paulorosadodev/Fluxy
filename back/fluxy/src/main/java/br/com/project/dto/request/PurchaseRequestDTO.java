package br.com.project.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record PurchaseRequestDTO(
        LocalDate date,
        LocalTime time,
        Integer total,
        Integer installments,
        String paymentType,
        Integer productAmount,

        Integer productId,
        String customerId,
        String employeeId
) {}
