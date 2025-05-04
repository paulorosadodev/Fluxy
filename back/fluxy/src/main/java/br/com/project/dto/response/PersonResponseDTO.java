package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {

    private Integer idPerson;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
}
