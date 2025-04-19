package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operacional {
    private Integer fkFuncionarioIdFuncionario; // PK e FK
    private String funcao;
}
