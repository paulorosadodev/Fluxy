package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juridico {

    private Integer fkClienteId; // <-- adiciona isso
    private String inscrEstadual;
    private String cnpj;
    private String razaoSocial;
}
