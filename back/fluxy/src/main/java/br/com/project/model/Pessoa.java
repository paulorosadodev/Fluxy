package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    private Integer idPessoa;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
}
