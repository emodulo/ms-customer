package br.com.emodulo.customer.application.usecase;

import br.com.emodulo.customer.domain.entity.Customer;
import br.com.emodulo.customer.domain.exception.CustomerDocumentAlreadyExists;
import br.com.emodulo.customer.domain.repository.ICustomerDatabaseAdapter;
import br.com.emodulo.customer.domain.usecase.IAddCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCustomerUseCase implements IAddCustomerUseCase {

    private final ICustomerDatabaseAdapter database;

    @Override
    public Customer execute(Customer customer) throws CustomerDocumentAlreadyExists {

        Customer customerDb = database.findByDocument(customer.getDocument());
        if(customerDb != null) {
            throw new CustomerDocumentAlreadyExists("Cliente já cadastrado!");
        }

        return database.save(customer);
    }
}
