package br.com.emodulo.customer.adapter.in.api.controller;


import br.com.emodulo.customer.adapter.in.api.dto.CustomerRequestDTO;
import br.com.emodulo.customer.adapter.in.api.dto.CustomerResponseDTO;
import br.com.emodulo.customer.adapter.in.api.mapper.CustomerDtoMapper;
import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerDocumentAlreadyExists;
import br.com.emodulo.customer.port.in.CustomerUseCasePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerUseCasePort service;
    private final CustomerDtoMapper mapper;

    @PostMapping()
    public ResponseEntity<?> addCustomer(@RequestBody CustomerRequestDTO dto, @AuthenticationPrincipal Jwt jwt) throws CustomerDocumentAlreadyExists {
        try {
            Customer customer = mapper.toDomain(dto, jwt);
            Customer created  = service.create(customer);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(created));

    } catch (CustomerDocumentAlreadyExists e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id,
                                            @RequestBody @Valid CustomerRequestDTO dto,
                                            @AuthenticationPrincipal Jwt jwt) {
        Customer existing = service.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.getExternalId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }

        Customer updated = mapper.toDomain(dto, jwt);

        updated.setExternalId(existing.getExternalId());
        updated.setEmail(existing.getEmail());

        updated.setAuthProvider(existing.getAuthProvider());

        return ResponseEntity.ok(mapper.toDto(service.update(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id,
                                            @AuthenticationPrincipal Jwt jwt) {
        Customer existing = service.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (!existing.getExternalId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id,
                                             @AuthenticationPrincipal Jwt jwt) {
        Customer customer = service.findById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        if (!customer.getExternalId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }

        return ResponseEntity.ok(mapper.toDto(customer));
    }

    @GetMapping
    public ResponseEntity<?> getOwnCustomer(@AuthenticationPrincipal Jwt jwt) {
        Customer customer = service.findByExternalId(jwt.getSubject());
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toDto(customer));
    }
}
