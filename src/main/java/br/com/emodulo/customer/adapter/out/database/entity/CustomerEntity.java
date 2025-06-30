package br.com.emodulo.customer.adapter.out.database.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "customers")
public class CustomerEntity {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("document")
    private String document;

    @Field("email")
    private String email;

    @Field("auth_provider")
    private String authProvider;

    @Field("external_id")
    private String externalId;

    @Field("address")
    private AddressEmbeddable address;
}
