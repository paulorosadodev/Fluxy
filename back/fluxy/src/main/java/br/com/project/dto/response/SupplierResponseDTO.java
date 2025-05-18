package br.com.project.dto.response;

import lombok.Data;

@Data
public class SupplierResponseDTO {
    private Integer id;
    private String cnpj;
    private String name;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
}
