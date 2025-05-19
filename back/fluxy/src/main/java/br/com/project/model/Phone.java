package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    private Integer idPhone;
    private Integer idPerson;
    private String number;
    public Phone(Integer idPerson, String number) {
        this.idPerson = idPerson;
        this.number = number;
    }

}
