package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    private Integer numero;
    private String data;
    private String hora;
    private Integer parcelas;
    private String tipo;
    private Integer qtdProduto;
    private Integer idProduto; // 🔥 FK para Produto
    private Integer idCliente; // 🔥 FK para Cliente
    private Integer idFuncionarioOperacional; // 🔥 FK para Funcionario
}
