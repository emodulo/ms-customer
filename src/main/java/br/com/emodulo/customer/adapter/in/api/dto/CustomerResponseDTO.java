package br.com.emodulo.customer.adapter.in.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerResponseDTO {

    private String id;
    private String name;
    private String document;
    private String email;
    private String authProvider;
    private String externalId;
    private AddressDTO address;
}
