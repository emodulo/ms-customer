package br.com.emodulo.customer.port.in;

import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerDocumentAlreadyExists;

public interface AddCustomerUseCase {
    Customer execute(Customer customer) throws CustomerDocumentAlreadyExists;
}
