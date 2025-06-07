package br.com.emodulo.ms_customer.infra;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByDocument(String document);

}
