package br.com.emodulo.customer.application.dto;

import br.com.emodulo.customer.domain.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
public class CustomerDTO {

    private UUID id;

    @NotBlank(message = "Nome do cliente não pode ser vazio")
    private String name;

    @NotBlank(message = "Documento do cliente não pode ser vazio")
    private String document;

    public CustomerDTO(UUID id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
    }

    public Customer toCustomer() {
        return new Customer(this.getId(), this.getName(), this.getDocument());
    }

    public CustomerDTO toDto(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getDocument());
    }
}
