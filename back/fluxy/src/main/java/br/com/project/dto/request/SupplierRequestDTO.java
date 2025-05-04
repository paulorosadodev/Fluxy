package br.com.project.dto.request;

import lombok.Data;

@Data
public class SupplierRequestDTO {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
    private String cnpj;
    private String name;
}
