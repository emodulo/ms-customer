package br.com.emodulo.customer.adapter.out.database;

import br.com.emodulo.customer.adapter.out.database.entity.CustomerEntity;
import br.com.emodulo.customer.adapter.out.database.mapper.CustomerEntityMapper;
import br.com.emodulo.customer.adapter.out.database.repository.CustomerJpaRepository;
import br.com.emodulo.customer.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerDatabaseAdapterTest {

    private CustomerJpaRepository repository;
    private CustomerEntityMapper mapper;
    private CustomerDatabaseAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerJpaRepository.class);
        mapper = mock(CustomerEntityMapper.class);
        adapter = new CustomerDatabaseAdapter(repository, mapper);
    }

    @Test
    void shouldSaveCustomer() {
        Customer domain = new Customer();
        CustomerEntity entity = new CustomerEntity();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        Customer saved = adapter.save(domain);

        assertThat(saved).isEqualTo(domain);
    }

    @Test
    void shouldUpdateCustomer() {
        Customer domain = new Customer();
        domain.setId("123");
        CustomerEntity entity = new CustomerEntity();

        when(repository.findById("123")).thenReturn(Optional.of(entity));
        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        Customer updated = adapter.update(domain);

        assertThat(updated).isEqualTo(domain);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentCustomer() {
        Customer domain = new Customer();
        domain.setId("999");

        when(repository.findById("999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.update(domain))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cliente não encontrado");
    }

    @Test
    void shouldDeleteCustomer() {
        adapter.delete("123");
        verify(repository).deleteById("123");
    }

    @Test
    void shouldFindCustomerById() {
        CustomerEntity entity = new CustomerEntity();
        Customer domain = new Customer();
        when(repository.findById("abc")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Customer result = adapter.findById("abc");

        assertThat(result).isEqualTo(domain);
    }

    @Test
    void shouldReturnNullWhenCustomerNotFound() {
        when(repository.findById("xyz")).thenReturn(Optional.empty());
        Customer result = adapter.findById("xyz");
        assertThat(result).isNull();
    }

    @Test
    void shouldFindByEmailOrDocumentAndIdNot() {
        CustomerEntity entity = new CustomerEntity();
        Customer domain = new Customer();

        when(repository.findByEmailOrDocumentAndIdNot("1", "email", "doc"))
                .thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        List<Customer> list = adapter.findByEmailOrDocumentAndIdNot("1", "email", "doc");

        assertThat(list).containsExactly(domain);
    }

    @Test
    void shouldFindByExternalId() {
        CustomerEntity entity = new CustomerEntity();
        Customer domain = new Customer();
        when(repository.findByExternalId("ext")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Customer result = adapter.findByExternalId("ext");

        assertThat(result).isEqualTo(domain);
    }

    @Test
    void shouldCheckExistsByEmailOrDocument() {
        when(repository.existsByEmailOrDocument("email", "doc")).thenReturn(true);
        boolean exists = adapter.existsByEmailOrDocument("email", "doc");
        assertThat(exists).isTrue();
    }
}
