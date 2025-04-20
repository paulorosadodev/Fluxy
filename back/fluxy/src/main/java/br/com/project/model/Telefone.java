package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {
    private String numero;
    private Integer idTelefone; // 🔥 FK para Pessoa
}
