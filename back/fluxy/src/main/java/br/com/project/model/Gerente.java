package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gerente {
    private Integer fkFuncionarioMatricula; // PK e FK
    private String setorAtuacao;
}
