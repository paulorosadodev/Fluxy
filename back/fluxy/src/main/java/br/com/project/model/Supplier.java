package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private Integer idSupplier;
    private String cnpj;
    private String name;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
}
