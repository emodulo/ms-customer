package br.com.emodulo.customer.adapter.out.database.mapper;

import br.com.emodulo.customer.adapter.out.database.entity.AddressEmbeddable;
import br.com.emodulo.customer.adapter.out.database.entity.CustomerEntity;
import br.com.emodulo.customer.domain.model.Address;
import br.com.emodulo.customer.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerEntityMapperTest {

    private CustomerEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CustomerEntityMapper();
    }

    @Test
    void shouldMapToEntity() {
        Address address = new Address("Rua X", "123", "Cidade", "Estado", "00000-000");
        Customer customer = new Customer();
        customer.setId("123");
        customer.setName("Maria");
        customer.setEmail("maria@email.com");
        customer.setDocument("12345678900");
        customer.setExternalId("ext-123");
        customer.setAuthProvider("cognito");
        customer.setAddress(address);

        CustomerEntity entity = mapper.toEntity(customer);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo("123");
        assertThat(entity.getName()).isEqualTo("Maria");
        assertThat(entity.getEmail()).isEqualTo("maria@email.com");
        assertThat(entity.getDocument()).isEqualTo("12345678900");
        assertThat(entity.getExternalId()).isEqualTo("ext-123");
        assertThat(entity.getAuthProvider()).isEqualTo("cognito");
        assertThat(entity.getAddress()).isNotNull();
        assertThat(entity.getAddress().getCity()).isEqualTo("Cidade");
    }

    @Test
    void shouldMapToDomain() {
        AddressEmbeddable address = new AddressEmbeddable("Rua Y", "321", "Cidade2", "Estado2", "99999-999");
        CustomerEntity entity = new CustomerEntity();
        entity.setId("321");
        entity.setName("João");
        entity.setEmail("joao@email.com");
        entity.setDocument("98765432100");
        entity.setExternalId("ext-321");
        entity.setAuthProvider("cognito");
        entity.setAddress(address);

        Customer customer = mapper.toDomain(entity);

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo("321");
        assertThat(customer.getName()).isEqualTo("João");
        assertThat(customer.getEmail()).isEqualTo("joao@email.com");
        assertThat(customer.getDocument()).isEqualTo("98765432100");
        assertThat(customer.getExternalId()).isEqualTo("ext-321");
        assertThat(customer.getAuthProvider()).isEqualTo("cognito");
        assertThat(customer.getAddress()).isNotNull();
        assertThat(customer.getAddress().getStreet()).isEqualTo("Rua Y");
    }
}
