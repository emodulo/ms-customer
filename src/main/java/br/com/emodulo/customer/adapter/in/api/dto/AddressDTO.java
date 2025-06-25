package br.com.emodulo.customer.adapter.in.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDTO {

    @NotBlank(message = "Street do cliente não pode ser vazio")
    private String street;

    @NotBlank(message = "Number do cliente não pode ser vazio")
    private String number;

    @NotBlank(message = "City do cliente não pode ser vazio")
    private String city;

    @NotBlank(message = "State do cliente não pode ser vazio")
    private String state;

    @NotBlank(message = "Zip do cliente não pode ser vazio")
    private String zip;
}
