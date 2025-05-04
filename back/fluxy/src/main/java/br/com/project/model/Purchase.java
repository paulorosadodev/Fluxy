package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    private Integer number;
    private LocalDate date;
    private LocalTime time;
    private Integer installments;
    private String paymentType;
    private Integer productQuantity;
    private Integer productId;
    private Integer clientId;
    private Integer operationalEmployeeId;
}
