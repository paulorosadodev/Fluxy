package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    private Integer numero;
    private Date data;
    private Integer hora; // Está como INT no banco
    private Integer parcelas;
    private String tipo; // forma de pagamento
    private Integer qtdProduto;
    private Integer fkProdutoId;
    private Integer fkClienteId;
    private Integer fkOperacionalIdFuncionario;
}
