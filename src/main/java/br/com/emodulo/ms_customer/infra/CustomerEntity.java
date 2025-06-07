package br.com.emodulo.ms_customer.infra;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Getter
@Setter
@Document(collection = "customer")
public class CustomerEntity {

    @MongoId()
    private UUID id;
    private String name;
    private String Document;

    public CustomerEntity(UUID id, String name, String document) {
        this.id = id;
        this.name = name;
        Document = document;
    }
}
