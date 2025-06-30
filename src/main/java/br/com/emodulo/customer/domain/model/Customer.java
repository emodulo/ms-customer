package br.com.emodulo.customer.domain.model;

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
    private String email;
    private String authProvider;
    private String externalId;

    private Address address;

    public Customer(String id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
    }

    public Customer(String id, String name, String document, String email, String authProvider, String externalId, Address address) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.email = email;
        this.authProvider = authProvider;
        this.externalId = externalId;
        this.address = address;
    }
}
