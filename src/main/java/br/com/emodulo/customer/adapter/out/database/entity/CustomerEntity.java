package br.com.emodulo.customer.adapter.out.database.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "customers")
public class CustomerEntity {

    @Id()
    private String id;
    private String name;
    private String document;

    public CustomerEntity(String id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
    }
}
