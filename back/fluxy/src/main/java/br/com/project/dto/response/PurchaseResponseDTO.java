package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDTO {
    private Integer number;
    private LocalDate date;
    private LocalTime time;
    private Integer installments;
    private String type;
    private Integer productAmount;

    private Integer productId;
    private Integer clientId;
    private Integer employee;
}
