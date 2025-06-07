package br.com.emodulo.customer.application.controller;


import br.com.emodulo.customer.application.dto.CustomerDTO;
import br.com.emodulo.customer.domain.entity.Customer;
import br.com.emodulo.customer.domain.usecase.IAddCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final IAddCustomerUseCase addCustomer;

    @PostMapping(produces = "application/json")
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customerDto) {
        try {
            Customer customer = customerDto.toCustomer();
            Customer customerDb = addCustomer.execute(customer);
            return ResponseEntity.status(HttpStatus.OK).body(customerDb);

        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
