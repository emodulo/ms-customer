package br.com.emodulo.customer.adapter.in.api.controller;


import br.com.emodulo.customer.adapter.in.api.config.ApiVersion;
import br.com.emodulo.customer.adapter.in.api.dto.CustomerRequestDTO;
import br.com.emodulo.customer.adapter.in.api.mapper.CustomerDtoMapper;
import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.exception.CustomerAlreadyExists;
import br.com.emodulo.customer.port.in.CustomerUseCasePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clientes", description = "Operações com clientes")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping(ApiVersion.V1 + "/customers")
public class CustomerController {

    private final CustomerUseCasePort service;
    private final CustomerDtoMapper mapper;

    public CustomerController(
            CustomerUseCasePort service,
            CustomerDtoMapper mapper
    ) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping()
    @Operation(summary = "Cadastrar cliente", description = "cadastrar novo cliente autenticado.")
    public ResponseEntity<?> addCustomer(
            @RequestBody CustomerRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    ) throws CustomerAlreadyExists {
        try {

            Customer existing = service.findByExternalId(jwt.getSubject());

            if (existing != null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
            }

            Customer customer = mapper.toDomain(dto, jwt);
            Customer created = service.create(customer);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(mapper.toDto(created));

        } catch (CustomerAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "atualizar cliente autenticado.")
    public ResponseEntity<?> updateCustomer(
            @PathVariable String id,
            @RequestBody @Valid CustomerRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    ) throws CustomerAlreadyExists {
        try {

            Customer existing = service.findById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }

            if (!existing.getExternalId().equals(jwt.getSubject())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
            }

            Customer updated = mapper.toDomain(dto, jwt);
            updated.setId(id);
            updated.setExternalId(existing.getExternalId());
            updated.setAuthProvider(existing.getAuthProvider());

            return ResponseEntity.ok(mapper.toDto(service.update(updated)));

        } catch (CustomerAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente", description = "excluir cliente autenticado.")
    public ResponseEntity<?> deleteCustomer(
            @PathVariable String id,
            @AuthenticationPrincipal Jwt jwt
    ) {
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
    @Operation(summary = "Recuperar cliente", description = "recuperar cliente autenticado.")
    public ResponseEntity<?> getCustomerById(
            @PathVariable String id,
            @AuthenticationPrincipal Jwt jwt
    ) {
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
    @Operation(summary = "Recuperar cliente", description = "recuperar cliente autenticado.")
    public ResponseEntity<?> getOwnCustomer(
            @AuthenticationPrincipal Jwt jwt
    ) {
        Customer customer = service.findByExternalId(jwt.getSubject());
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toDto(customer));
    }
}
