package br.com.emodulo.customer.domain.repository;

import br.com.emodulo.customer.domain.entity.Customer;

public interface ICustomerDatabaseAdapter {
    Customer save(Customer customer);
    Customer findById(String id);
    Customer findByDocument(String document);
}