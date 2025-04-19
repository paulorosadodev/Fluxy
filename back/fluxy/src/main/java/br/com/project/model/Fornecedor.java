package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {
    private Integer idFornecedor; // também é idPessoa
    private String cnpj;
    private String nome;
}
