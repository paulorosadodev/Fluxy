package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fisico {

    private Integer fkClienteId; // <-- precisa adicionar esse!
    private String nome;
    private String cpf;
}
