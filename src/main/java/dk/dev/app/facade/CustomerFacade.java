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
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerFacade(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer entity = customerMapper.toEntity(customerDto);
        entity = customerService.addCustomer(entity);
        return customerMapper.toDto(entity);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer entity = customerMapper.toEntity(customerDto);
        entity = customerService.updateCustomer(id, entity);
        return entity != null ? customerMapper.toDto(entity) : null;
    }

    public Boolean deleteCustomer(Long id) {
        return customerService.deleteCustomer(id);
    }

    public CustomerDto getCustomerById(Long id) {
        Customer entity = customerService.getCustomerById(id);
        return entity != null ? customerMapper.toDto(entity) : null;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }
}
