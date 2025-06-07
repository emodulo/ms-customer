package br.com.emodulo.customer.infra;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByDocument(String document);

}
