package br.com.emodulo.customer.application.service;

import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerDocumentAlreadyExists;
import br.com.emodulo.customer.port.out.CustomerRepositoryPort;
import br.com.emodulo.customer.port.in.CustomerUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerUseCasePort {

    private final CustomerRepositoryPort repository;

    @Override
    public Customer create(Customer customer) throws CustomerDocumentAlreadyExists {
        Customer existing = repository.findByDocument(customer.getDocument());
        if (existing != null) {
            throw new CustomerDocumentAlreadyExists("Cliente já cadastrado com este documento");
        }
        return repository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        // Você pode adicionar validações aqui (ex: garantir que externalId seja o mesmo)
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
    public Customer findByDocument(String document) {
        return repository.findByDocument(document);
    }

    @Override
    public Customer findByExternalId(String externalId) {
        return repository.findByExternalId(externalId);
    }
}
