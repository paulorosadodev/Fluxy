package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer idPerson;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
}
