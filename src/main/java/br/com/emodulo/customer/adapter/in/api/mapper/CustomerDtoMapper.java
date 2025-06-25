package br.com.emodulo.customer.adapter.in.api.mapper;

import br.com.emodulo.customer.adapter.in.api.dto.AddressDTO;
import br.com.emodulo.customer.adapter.in.api.dto.CustomerRequestDTO;
import br.com.emodulo.customer.adapter.in.api.dto.CustomerResponseDTO;
import br.com.emodulo.customer.domain.model.Address;
import br.com.emodulo.customer.domain.model.Customer;
import org.springframework.security.oauth2.jwt.Jwt;

public class CustomerDtoMapper {

    public Customer toDomain(CustomerRequestDTO dto, Jwt jwt) {
        if (dto == null) return null;

        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setExternalId(jwt.getSubject());
        customer.setEmail(jwt.getClaim("email"));
        customer.setName(dto.getName());
        customer.setDocument(dto.getDocument());
        customer.setAuthProvider("cognito");

        if (dto.getAddress() != null) {
            Address address = new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setNumber(dto.getAddress().getNumber());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setZip(dto.getAddress().getZip());
            customer.setAddress(address);
        }

        return customer;
    }

    public CustomerResponseDTO toDto(Customer domain) {
        if (domain == null) return null;

        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setDocument(domain.getDocument());
        dto.setEmail(domain.getEmail());
        dto.setExternalId(domain.getExternalId());

        if (domain.getAddress() != null) {
            AddressDTO addressDto = new AddressDTO();
            addressDto.setStreet(domain.getAddress().getStreet());
            addressDto.setNumber(domain.getAddress().getNumber());
            addressDto.setCity(domain.getAddress().getCity());
            addressDto.setState(domain.getAddress().getState());
            addressDto.setZip(domain.getAddress().getZip());
            dto.setAddress(addressDto);
        }

        return dto;
    }
}
