package br.com.emodulo.customer.port.out;

import br.com.emodulo.customer.domain.model.Customer;

import java.util.List;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Customer update(Customer customer);
    void delete(String id);

    Customer findById(String id);
    List<Customer> findByEmailOrDocumentAndIdNot(String id, String email, String document);
    Customer findByExternalId(String externalId);
    boolean existsByEmailOrDocument(String email, String document);
}