package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    private Integer number;
    private String date;
    private String hour;
    private Integer installments;
    private String type;
    private Integer product;

    private Integer idProduct;
    private Integer idClient;
    private Integer idEmployeeOperational;
}
