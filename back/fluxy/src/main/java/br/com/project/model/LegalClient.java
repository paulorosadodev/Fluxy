package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalClient {
    private Integer id; // id_cliente
    private String corporateName; // razao social
    private String cnpj;
    private String stateRegistration; // inscrição estadual
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
}
