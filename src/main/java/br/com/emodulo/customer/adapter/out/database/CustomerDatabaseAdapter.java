package br.com.emodulo.customer.adapter.out.database;

import br.com.emodulo.customer.adapter.out.database.mapper.CustomerEntityMapper;
import br.com.emodulo.customer.adapter.out.database.repository.CustomerJpaRepository;
import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.port.out.CustomerRepositoryPort;
import br.com.emodulo.customer.adapter.out.database.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDatabaseAdapter implements CustomerRepositoryPort {

    private final CustomerJpaRepository repository;
    private final CustomerEntityMapper mapper;

    public CustomerDatabaseAdapter(CustomerJpaRepository repository, CustomerEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Customer update(Customer customer) {
        Optional<CustomerEntity> existing = repository.findById(customer.getId());
        if (existing.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado para atualização");
        }

        CustomerEntity entity = mapper.toEntity(customer);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public Customer findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Customer> findByEmailOrDocumentAndIdNot(String id, String email, String document) {
        return repository.findByEmailOrDocumentAndIdNot( id, email, document)
                .stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public Customer findByExternalId(String externalId) {
        return repository.findByExternalId(externalId)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public boolean existsByEmailOrDocument(String email, String document) {
        return repository.existsByEmailOrDocument(email, document);
    }
}
