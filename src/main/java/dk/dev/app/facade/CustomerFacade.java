package dk.dev.app.facade;

import dk.dev.app.dto.CustomerDto;
import dk.dev.app.mapper.CustomerMapper;
import dk.dev.app.model.Customer;
import dk.dev.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerFacade {
    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @Autowired
    public CustomerFacade(CustomerService customerService, CustomerMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer entity = mapper.toEntity(customerDto);
        entity = customerService.addCustomer(entity);
        return mapper.toDto(entity);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer entity = mapper.toEntity(customerDto);
        entity = customerService.updateCustomer(id, entity);
        return entity != null ? mapper.toDto(entity) : null;
    }

    public Boolean deleteCustomer(Long id) {
        return customerService.deleteCustomer(id);
    }

    public CustomerDto getCustomerById(Long id) {
        Customer entity = customerService.getCustomerById(id);
        return entity != null ? mapper.toDto(entity) : null;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
