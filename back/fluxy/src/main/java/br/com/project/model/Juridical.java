package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juridical {

    private Integer fkClientId; // <-- adiciona isso
    private String stateRegistration;
    private String cnpj;
    private String socialName;
}
