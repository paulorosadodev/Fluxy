package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    private Integer idFuncionario;
    private Integer idPessoa; // 🔥 FK para Pessoa
    private String matricula;
    private String nome;
    private String cpf;
    private Double salario;
    private String setor;
    private String turno;
    private String funcao;
    private Integer idSupervisor; // 🔥 FK para outro Funcionário
}
