package br.com.emodulo.customer.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String street;
    private String number;
    private String city;
    private String state;
    private String zip;
}
