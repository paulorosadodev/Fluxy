package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fisico {
    private Integer fkClienteId; // PK e FK
    private String nome;
    private String cpf;
}
