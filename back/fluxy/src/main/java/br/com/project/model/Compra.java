package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    private Integer number;
    private String date;
    private String hour;
    private Integer parcelas;
    private String type;
    private Integer product;
    private Integer idProduto; // 🔥 FK para Produto
    private Integer idCliente; // 🔥 FK para Cliente
    private Integer idFuncionarioOperacional; // 🔥 FK para Funcionario
}
