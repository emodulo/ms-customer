package br.com.emodulo.customer.adapter.out.database.mapper;

import br.com.emodulo.customer.adapter.out.database.entity.AddressEmbeddable;
import br.com.emodulo.customer.adapter.out.database.entity.CustomerEntity;
import br.com.emodulo.customer.domain.model.Address;
import br.com.emodulo.customer.domain.model.Customer;

public class CustomerEntityMapper {
    public CustomerEntity toEntity(Customer domain) {
        if (domain == null) return null;

        CustomerEntity entity = new CustomerEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDocument(domain.getDocument());
        entity.setEmail(domain.getEmail());
        entity.setExternalId(domain.getExternalId());
        entity.setAuthProvider(domain.getAuthProvider());

        if (domain.getAddress() != null) {
            AddressEmbeddable address = new AddressEmbeddable();
            address.setStreet(domain.getAddress().getStreet());
            address.setNumber(domain.getAddress().getNumber());
            address.setCity(domain.getAddress().getCity());
            address.setState(domain.getAddress().getState());
            address.setZip(domain.getAddress().getZip());
            entity.setAddress(address);
        }

        return entity;
    }

    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;

        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setName(entity.getName());
        customer.setDocument(entity.getDocument());
        customer.setEmail(entity.getEmail());
        customer.setExternalId(entity.getExternalId());
        customer.setAuthProvider(entity.getAuthProvider());

        if (entity.getAddress() != null) {
            Address address = new Address();
            address.setStreet(entity.getAddress().getStreet());
            address.setNumber(entity.getAddress().getNumber());
            address.setCity(entity.getAddress().getCity());
            address.setState(entity.getAddress().getState());
            address.setZip(entity.getAddress().getZip());
            customer.setAddress(address);
        }

        return customer;
    }
}
