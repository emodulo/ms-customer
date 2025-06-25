package br.com.emodulo.customer.application.service;

import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerAlreadyExists;
import br.com.emodulo.customer.port.out.CustomerRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerRepositoryPort repository;
    private CustomerService service;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerRepositoryPort.class);
        service = new CustomerService(repository);
    }

    @Test
    void shouldCreateCustomerSuccessfully() throws CustomerAlreadyExists {
        Customer customer = new Customer();
        customer.setEmail("email@email.com");
        customer.setDocument("123456789");

        when(repository.existsByEmailOrDocument(customer.getEmail(), customer.getDocument())).thenReturn(false);
        when(repository.save(customer)).thenReturn(customer);

        Customer created = service.create(customer);

        assertThat(created).isEqualTo(customer);
        verify(repository).save(customer);
    }

    @Test
    void shouldThrowWhenCreatingDuplicateCustomer() {
        Customer customer = new Customer();
        customer.setEmail("email@email.com");
        customer.setDocument("123456789");

        when(repository.existsByEmailOrDocument(customer.getEmail(), customer.getDocument())).thenReturn(true);

        assertThrows(CustomerAlreadyExists.class, () -> service.create(customer));
        verify(repository, never()).save(any());
    }

    @Test
    void shouldUpdateCustomerSuccessfully() throws CustomerAlreadyExists {
        Customer customer = new Customer();
        customer.setId("1");
        customer.setEmail("email@email.com");
        customer.setDocument("123456789");

        when(repository.findByEmailOrDocumentAndIdNot("1", customer.getEmail(), customer.getDocument()))
                .thenReturn(Collections.emptyList());
        when(repository.update(customer)).thenReturn(customer);

        Customer updated = service.update(customer);

        assertThat(updated).isEqualTo(customer);
        verify(repository).update(customer);
    }

    @Test
    void shouldThrowWhenUpdatingWithConflictingData() {
        Customer customer = new Customer();
        customer.setId("1");
        customer.setEmail("email@email.com");
        customer.setDocument("123456789");

        when(repository.findByEmailOrDocumentAndIdNot("1", customer.getEmail(), customer.getDocument()))
                .thenReturn(List.of(new Customer()));

        assertThrows(CustomerAlreadyExists.class, () -> service.update(customer));
        verify(repository, never()).update(any());
    }

    @Test
    void shouldDeleteCustomer() {
        service.delete("123");
        verify(repository).delete("123");
    }

    @Test
    void shouldFindCustomerById() {
        Customer customer = new Customer();
        when(repository.findById("abc")).thenReturn(customer);

        Customer result = service.findById("abc");

        assertThat(result).isEqualTo(customer);
    }

    @Test
    void shouldFindCustomerByExternalId() {
        Customer customer = new Customer();
        when(repository.findByExternalId("ext-id")).thenReturn(customer);

        Customer result = service.findByExternalId("ext-id");

        assertThat(result).isEqualTo(customer);
    }
}
