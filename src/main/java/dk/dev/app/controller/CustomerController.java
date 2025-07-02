package dk.dev.app.controller;

import dk.dev.app.dto.CustomerDto;
import dk.dev.app.dto.LegalCustomerDto;
import dk.dev.app.dto.RealCustomerDto;
import dk.dev.app.facade.CustomerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerFacade facade;

    @Autowired
    public CustomerController(CustomerFacade facade) {
        this.facade = facade;
    }

    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers")
    @GetMapping
    public List<CustomerDto> getCustomers() {
        return facade.getAllCustomers();
    }


    @Operation(summary = "Get customer by ID", description = "Retrieve a customer by its unique identifier")
    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable("id") Long id) {
        return facade.getCustomerById(id);
    }

    @Operation(summary = "Delete a customer", description = "Remove a customer from the customer system")
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        Boolean success = facade.deleteCustomer(id);
        if (success) {
            return "Customer with id " + id + " successfully deleted";
        } else
            return "Customer with id " + id + " could not be deleted";
    }

    @Operation(summary = "Add a new customer", description = "Create a new customer")
    @PostMapping
    public CustomerDto createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer object to be added",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {
                                            RealCustomerDto.class,
                                            LegalCustomerDto.class
                                    }
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Real Customer Example",
                                            value = "{"
                                                    + "\"fullName\": \"John Doe\","
                                                    + "\"phoneNumber\": \"+1234567890\","
                                                    + "\"email\": \"john@gmail.com\","
                                                    + "\"type\": \"REAL\","
                                                    + "\"nationality\": \"UK\","
                                                    + "\"age\": \"43\""
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Legal Customer Example",
                                            value = "{"
                                                    + "\"fullName\": \"John Doe\","
                                                    + "\"phoneNumber\": \"+1234567890\","
                                                    + "\"email\": \"john@gmail.com\","
                                                    + "\"type\": \"LEGAL\","
                                                    + "\"companyName\": \"IBS CO\","
                                                    + "\"industry\": \"IT\""
                                                    + "}"
                                    )
                            }
                    )
            )
            @RequestBody CustomerDto customerDto) {
        return facade.addCustomer(customerDto);

    }

    @Operation(summary = "update a new customer", description = "update customer")
    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer object to be added",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {
                                            RealCustomerDto.class,
                                            LegalCustomerDto.class
                                    }
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Real Customer Example",
                                            value = "{"
                                                    + "\"fullName\": \"John Doe\","
                                                    + "\"phoneNumber\": \"+1234567890\","
                                                    + "\"email\": \"john@gmail.com\","
                                                    + "\"type\": \"REAL\","
                                                    + "\"nationality\": \"UK\","
                                                    + "\"age\": \"43\""
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Legal Customer Example",
                                            value = "{"
                                                    + "\"fullName\": \"John Doe\","
                                                    + "\"phoneNumber\": \"+1234567890\","
                                                    + "\"email\": \"john@gmail.com\","
                                                    + "\"type\": \"LEGAL\","
                                                    + "\"companyName\": \"IBS CO\","
                                                    + "\"industry\": \"IT\""
                                                    + "}"
                                    )
                            }
                    )
            )
            @RequestBody CustomerDto customerDto
    )
    {
        return facade.updateCustomer(id, customerDto);
    }

    @Operation(summary = "Search customers by full name", description = "search customers by full name")
    @GetMapping("/{fullName}")
    public List<CustomerDto> getCustomerByFullName(@PathVariable("fullName") String fullName) {
        return facade.getCustomerByFullName(fullName);
    }


}
