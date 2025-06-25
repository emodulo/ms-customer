package br.com.emodulo.customer.adapter.out.database.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AddressEmbeddable {

    @Field("street")
    private String street;

    @Field("number")
    private String number;

    @Field("city")
    private String city;

    @Field("state")
    private String state;

    @Field("zip")
    private String zip;

    public AddressEmbeddable() {
    }

    public AddressEmbeddable(String street, String number, String city, String state, String zip) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}