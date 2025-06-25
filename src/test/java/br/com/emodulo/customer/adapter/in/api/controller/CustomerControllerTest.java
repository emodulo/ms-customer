package br.com.emodulo.customer.adapter.in.api.controller;

import br.com.emodulo.customer.adapter.in.api.dto.AddressDTO;
import br.com.emodulo.customer.adapter.in.api.dto.CustomerRequestDTO;
import br.com.emodulo.customer.adapter.in.api.dto.CustomerResponseDTO;
import br.com.emodulo.customer.adapter.in.api.mapper.CustomerDtoMapper;
import br.com.emodulo.customer.domain.model.Customer;
import br.com.emodulo.customer.port.in.CustomerUseCasePort;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    private CustomerUseCasePort service;
    private CustomerDtoMapper mapper;
    private CustomerController controller;

    @BeforeEach
    void setUp() {
        service = mock(CustomerUseCasePort.class);
        mapper = new CustomerDtoMapper();
        controller = new CustomerController(service, mapper);
    }

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {
        CustomerRequestDTO request = new CustomerRequestDTO();
        request.setName("Carlos");
        request.setEmail("carlos@email.com");
        request.setDocument("12345678901");
        request.setAddress(new AddressDTO("Rua A", "123", "Cidade", "Estado", "00000-000"));

        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("cognito-abc-123");
        when(service.findByExternalId("cognito-abc-123")).thenReturn(null);

        Customer created = mapper.toDomain(request, jwt);
        created.setId("id123");
        when(service.create(any(Customer.class))).thenReturn(created);

        ResponseEntity<?> response = controller.addCustomer(request, jwt);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        CustomerResponseDTO body = (CustomerResponseDTO) response.getBody();
        assertThat(body.getName()).isEqualTo("Carlos");
        assertThat(body.getDocument()).isEqualTo("12345678901");
    }

    @Test
    void shouldNotCreateIfCustomerAlreadyExists() throws Exception {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("cognito-sub");

        Customer existing = new Customer();
        existing.setExternalId("cognito-sub");

        when(service.findByExternalId("cognito-sub")).thenReturn(existing);

        CustomerRequestDTO dto = new CustomerRequestDTO();
        ResponseEntity<?> response = controller.addCustomer(dto, jwt);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldReturnNotFoundWhenCustomerDoesNotExistById() {
        when(service.findById("id123")).thenReturn(null);
        Jwt jwt = mock(Jwt.class);

        ResponseEntity<?> response = controller.getCustomerById("id123", jwt);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnForbiddenIfJwtDoesNotMatch() {
        Customer c = new Customer();
        c.setExternalId("user-1");
        when(service.findById("id1")).thenReturn(c);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("user-2");

        ResponseEntity<?> response = controller.getCustomerById("id1", jwt);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldUpdateCustomerSuccessfully() throws Exception {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("external-123");

        Customer existing = new Customer();
        existing.setId("id123");
        existing.setExternalId("external-123");
        existing.setAuthProvider("cognito");

        when(service.findById("id123")).thenReturn(existing);
        when(service.update(any(Customer.class))).thenReturn(existing);

        CustomerRequestDTO dto = createRequest();

        ResponseEntity<?> response = controller.updateCustomer("id123", dto, jwt);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldDeleteCustomerSuccessfully() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("external-123");

        Customer existing = new Customer();
        existing.setId("id123");
        existing.setExternalId("external-123");

        when(service.findById("id123")).thenReturn(existing);

        ResponseEntity<?> response = controller.deleteCustomer("id123", jwt);

        verify(service).delete("id123");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldGetOwnCustomer() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("external-123");

        Customer existing = new Customer();
        existing.setId("id123");
        existing.setExternalId("external-123");
        when(service.findByExternalId("external-123")).thenReturn(existing);

        ResponseEntity<?> response = controller.getOwnCustomer(jwt);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private CustomerRequestDTO createRequest() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("Carlos");
        dto.setEmail("carlos@email.com");
        dto.setDocument("12345678901");
        dto.setAddress(new AddressDTO("Rua A", "123", "Cidade", "Estado", "00000-000"));
        return dto;
    }
}
