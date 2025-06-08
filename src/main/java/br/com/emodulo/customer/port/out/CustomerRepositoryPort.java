package br.com.emodulo.customer.port.out;

import br.com.emodulo.customer.domain.model.Customer;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Customer findById(String id);
    Customer findByDocument(String document);
}