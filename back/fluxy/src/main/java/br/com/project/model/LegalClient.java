package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalClient {
    private Integer id;
    private String legalName;
    private String cnpj;
    private String stateRegistration;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
    private List<String> phone;
}
