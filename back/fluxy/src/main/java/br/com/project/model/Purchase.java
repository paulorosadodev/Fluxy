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
    private Integer number;        // número único da compra (PK)
    private LocalDate date;         // data da compra
    private LocalTime time;         // hora da compra
    private Integer installments;   // número de parcelas
    private String paymentType;     // tipo de pagamento (ex: crédito, débito)
    private Integer productQuantity; // quantidade de produtos
    private Integer productId;      // ID do produto comprado (FK)
    private Integer clientId;       // ID do cliente (FK)
    private Integer operationalEmployeeId; // ID do funcionário operacional (FK)
}
