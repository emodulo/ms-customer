package br.com.emodulo.customer.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Customer {

    private UUID id;
    private String name;
    private String document;

    public Customer(UUID id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
    }
}
