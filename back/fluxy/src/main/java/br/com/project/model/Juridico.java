package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Juridico {
    private Integer fkClienteId; // PK e FK
    private String inscrEstadual;
    private String cnpj;
    private String razaoSocial;
}
