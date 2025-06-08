package br.com.emodulo.customer.adapter.out.database;

import br.com.emodulo.customer.adapter.out.database.repository.CustomerRepository;
import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.port.out.CustomerRepositoryPort;
import br.com.emodulo.customer.adapter.out.database.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final CustomerRepository repository;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = toCustomerEntity(customer);
        CustomerEntity entity = repository.save(customerEntity);
        return toCustomer(entity);
    }
    @Override
    public Customer findById(String id) {
        Customer customer = null;
        Optional<CustomerEntity> customerEntity = repository.findById(id);

        if(customerEntity.isPresent()) {
            customer = toCustomer(customerEntity.get());
        }

        return customer;
    }

    @Override
    public Customer findByDocument(String document) {
        Customer customer = null;
        Optional<CustomerEntity> customerEntity = repository.findByDocument(document);

        if(customerEntity.isPresent()) {
            customer = toCustomer(customerEntity.get());
        }

        return customer;
    }

    CustomerEntity toCustomerEntity(Customer customer) {
        CustomerEntity entity = null;
        if (customer != null) {
            entity = new CustomerEntity(
                    customer.getId(),
                    customer.getName(),
                    customer.getDocument());
        };
        return entity;
    }

    Customer toCustomer(CustomerEntity entity) {
        Customer customer = null;

        if (entity != null) {
            customer = new Customer(
                    entity.getId(),
                    entity.getName(),
                    entity.getDocument());
        };
        return customer;
    }
}
