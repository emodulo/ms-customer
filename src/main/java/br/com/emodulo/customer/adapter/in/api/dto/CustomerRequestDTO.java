package br.com.emodulo.customer.adapter.in.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequestDTO {

    @NotBlank(message = "Nome do cliente não pode ser vazio")
    private String name;

    @NotBlank(message = "Documento do cliente não pode ser vazio")
    private String document;

    @NotBlank(message = "Email do cliente não pode ser vazio")
    private String email;

    private AddressDTO address;
}
