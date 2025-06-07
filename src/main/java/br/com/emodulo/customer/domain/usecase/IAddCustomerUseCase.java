package br.com.emodulo.customer.domain.usecase;

import br.com.emodulo.customer.domain.entity.Customer;
import br.com.emodulo.customer.domain.exception.CustomerDocumentAlreadyExists;

public interface IAddCustomerUseCase {
    Customer execute(Customer customer) throws CustomerDocumentAlreadyExists;
}
