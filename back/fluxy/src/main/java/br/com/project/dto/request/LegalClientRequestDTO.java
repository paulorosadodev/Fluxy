package br.com.project.dto.request;

import lombok.Data;

@Data
public class LegalClientRequestDTO {
    private String corporateName;
    private String cnpj;
    private String stateRegistration;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
}
