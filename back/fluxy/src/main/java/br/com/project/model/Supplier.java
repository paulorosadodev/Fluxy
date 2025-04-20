package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private Integer idSupplier;
    private Integer idPerson; // 🔥 FK para Pessoa
    private String cnpj;
    private String name;
}
