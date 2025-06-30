package br.com.emodulo.customer.application.service;

import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerAlreadyExists;
import br.com.emodulo.customer.port.out.CustomerRepositoryPort;
import br.com.emodulo.customer.port.in.CustomerUseCasePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerUseCasePort {

    private final CustomerRepositoryPort repository;

    public CustomerService(CustomerRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Customer create(Customer customer) throws CustomerAlreadyExists {

        boolean existing = repository.existsByEmailOrDocument(customer.getEmail(), customer.getDocument());

        if (existing) {
            throw new CustomerAlreadyExists("Cliente já cadastrado.");
        }

        return repository.save(customer);
    }

    @Override
    public Customer update(Customer customer) throws CustomerAlreadyExists {

        List<Customer> existing = repository.findByEmailOrDocumentAndIdNot(
                customer.getId(),
                customer.getEmail(),
                customer.getDocument());

        if (!existing.isEmpty()) {
            throw new CustomerAlreadyExists("Email ou documento já está sendo usado por outro cliente.");
        }

        return repository.update(customer);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public Customer findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Customer findByExternalId(String externalId) {
        return repository.findByExternalId(externalId);
    }
}
