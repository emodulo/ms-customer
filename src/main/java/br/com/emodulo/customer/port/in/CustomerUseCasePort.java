package br.com.emodulo.customer.port.in;

import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerAlreadyExists;

public interface CustomerUseCasePort {
    Customer create(Customer customer) throws CustomerAlreadyExists;

    Customer update(Customer customer) throws CustomerAlreadyExists;

    void delete(String id);

    Customer findById(String id);

    Customer findByExternalId(String externalId);
}
