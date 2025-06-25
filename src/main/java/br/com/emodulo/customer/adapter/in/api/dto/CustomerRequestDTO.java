package br.com.emodulo.customer.adapter.in.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequestDTO {

    private String id;

    @NotBlank(message = "Nome do cliente não pode ser vazio")
    private String name;

    @NotBlank(message = "Documento do cliente não pode ser vazio")
    private String document;

    private AddressDTO address;
}
