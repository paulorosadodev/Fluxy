package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Physique {

    private Integer fkClientId; // <-- precisa adicionar esse!
    private String name;
    private String cpf;
}
