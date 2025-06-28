package br.com.emodulo.customer.adapter.in.api.mapper;

import br.com.emodulo.customer.adapter.in.api.dto.AddressDTO;
import br.com.emodulo.customer.adapter.in.api.dto.CustomerRequestDTO;
import br.com.emodulo.customer.adapter.in.api.dto.CustomerResponseDTO;
import br.com.emodulo.customer.domain.model.Address;
import br.com.emodulo.customer.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerDtoMapperTest {

    private CustomerDtoMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new CustomerDtoMapper();
    }

    @Test
    void shouldMapDtoToDomain() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("João");
        dto.setEmail("joao@email.com");
        dto.setDocument("12345678900");

        AddressDTO address = new AddressDTO();
        address.setStreet("Rua A");
        address.setNumber("123");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZip("01000-000");
        dto.setAddress(address);

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("user-sub-123");

        Customer customer = mapper.toDomain(dto, jwt);

        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo("João");
        assertThat(customer.getEmail()).isEqualTo("joao@email.com");
        assertThat(customer.getDocument()).isEqualTo("12345678900");
        assertThat(customer.getExternalId()).isEqualTo("user-sub-123");
        assertThat(customer.getAuthProvider()).isEqualTo("cognito");

        Address resultAddress = customer.getAddress();
        assertThat(resultAddress).isNotNull();
        assertThat(resultAddress.getStreet()).isEqualTo("Rua A");
        assertThat(resultAddress.getNumber()).isEqualTo("123");
        assertThat(resultAddress.getCity()).isEqualTo("São Paulo");
        assertThat(resultAddress.getState()).isEqualTo("SP");
        assertThat(resultAddress.getZip()).isEqualTo("01000-000");
    }

    @Test
    void shouldMapDomainToDto() {
        Customer customer = new Customer();
        customer.setId("abc123");
        customer.setName("Maria");
        customer.setEmail("maria@email.com");
        customer.setDocument("98765432100");
        customer.setExternalId("user-sub-456");
        customer.setAuthProvider("cognito");

        Address address = new Address();
        address.setStreet("Rua B");
        address.setNumber("456");
        address.setCity("Rio de Janeiro");
        address.setState("RJ");
        address.setZip("20000-000");
        customer.setAddress(address);

        CustomerResponseDTO dto = mapper.toDto(customer);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo("abc123");
        assertThat(dto.getName()).isEqualTo("Maria");
        assertThat(dto.getEmail()).isEqualTo("maria@email.com");
        assertThat(dto.getDocument()).isEqualTo("98765432100");
        assertThat(dto.getExternalId()).isEqualTo("user-sub-456");
        assertThat(dto.getAuthProvider()).isEqualTo("cognito");

        AddressDTO resultAddress = dto.getAddress();
        assertThat(resultAddress).isNotNull();
        assertThat(resultAddress.getStreet()).isEqualTo("Rua B");
        assertThat(resultAddress.getNumber()).isEqualTo("456");
        assertThat(resultAddress.getCity()).isEqualTo("Rio de Janeiro");
        assertThat(resultAddress.getState()).isEqualTo("RJ");
        assertThat(resultAddress.getZip()).isEqualTo("20000-000");
    }
}
