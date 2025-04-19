package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    private Integer idFuncionario; // também é idPessoa
    private String matricula;
    private String cpf;
    private String nome;
    private Integer salario;
    private Integer idSupervisor; // pode ser nulo
}
