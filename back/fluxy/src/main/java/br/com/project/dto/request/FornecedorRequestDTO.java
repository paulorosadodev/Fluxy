package br.com.project.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class FornecedorRequestDTO {
    private String cnpj;
    private String nome;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
    private List<String> telefones; // <-- agora vem telefones também!
}
