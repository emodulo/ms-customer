package br.com.emodulo.customer.application.service;

import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerDocumentAlreadyExists;
import br.com.emodulo.customer.port.out.CustomerRepositoryPort;
import br.com.emodulo.customer.port.in.AddCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCustomerService implements AddCustomerUseCase {

    private final CustomerRepositoryPort database;

    @Override
    public Customer execute(Customer customer) throws CustomerDocumentAlreadyExists {

        Customer customerDb = database.findByDocument(customer.getDocument());
        if(customerDb != null) {
            throw new CustomerDocumentAlreadyExists("Cliente já cadastrado!");
        }

        return database.save(customer);
    }
}
