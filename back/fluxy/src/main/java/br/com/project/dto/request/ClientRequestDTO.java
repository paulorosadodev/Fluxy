package br.com.project.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class ClientRequestDTO {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
    private List<String> phone;
}
