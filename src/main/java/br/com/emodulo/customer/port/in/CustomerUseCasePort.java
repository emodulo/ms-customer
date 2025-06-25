package br.com.emodulo.customer.port.in;

import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerDocumentAlreadyExists;

public interface CustomerUseCasePort {
    Customer create(Customer customer) throws CustomerDocumentAlreadyExists;

    Customer update(Customer customer);

    void delete(String id);

    Customer findById(String id);

    Customer findByDocument(String document);

    Customer findByExternalId(String externalId);
}
