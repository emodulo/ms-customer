package br.com.emodulo.ms_customer.domain.repository;

import br.com.emodulo.ms_customer.domain.entity.Customer;

import java.util.UUID;

public interface ICustomerDatabaseAdapter {
    Customer save(Customer customer);
    Customer findById(String id);
    Customer findByDocument(String document);
}