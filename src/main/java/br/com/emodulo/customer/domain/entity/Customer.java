package br.com.emodulo.customer.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer {

    private String id;
    private String name;
    private String document;

    public Customer(String id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
    }
}
