package br.com.emodulo.customer.adapter.out.database.repository;
import br.com.emodulo.customer.adapter.out.database.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByDocument(String document);

}
