package br.com.emodulo.customer.adapter.out.database.entity;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
}