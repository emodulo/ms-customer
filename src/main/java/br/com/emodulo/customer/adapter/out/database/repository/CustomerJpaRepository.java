package br.com.emodulo.customer.adapter.out.database.repository;
import br.com.emodulo.customer.adapter.out.database.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends MongoRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByExternalId(String externalId);

    @Query("{ '_id': { $ne: ?0 }, $or: [ { 'email': ?1 }, { 'document': ?2 } ] }")
    List<CustomerEntity> findByEmailOrDocumentAndIdNot( String id, String email, String document);

    boolean existsByEmailOrDocument(String email, String document);

}
