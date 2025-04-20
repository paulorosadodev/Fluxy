package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    private Integer number;
    private String date;
    private String hour;
    private Integer installments;
    private String type;
    private Integer product;
    private String productAmount;

    private Integer idProduct;
    private Integer idClient;
    private Integer idEmployeeOperational;

    public Purchase(int numero, String data, String hora, int parcelas, String tipo, int qtdProduto, int fkProdutoId, int fkClienteId, int fkOperacionalIdFuncionario) {
    }
}
