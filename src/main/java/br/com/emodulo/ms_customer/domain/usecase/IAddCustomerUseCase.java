package br.com.emodulo.ms_customer.domain.usecase;

import br.com.emodulo.ms_customer.domain.entity.Customer;
import br.com.emodulo.ms_customer.domain.exception.CustomerDocumentAlreadyExists;

public interface IAddCustomerUseCase {
    Customer execute(Customer customer) throws CustomerDocumentAlreadyExists;
}
