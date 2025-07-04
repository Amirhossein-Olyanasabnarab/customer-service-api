package dk.dev.app.facade;

import dk.dev.app.dto.CustomerDto;
import dk.dev.app.mapper.CustomerMapper;
import dk.dev.app.model.Customer;
import dk.dev.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerFacade {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;


    @Autowired
    public CustomerFacade(CustomerMapper customerMapper, CustomerService customerService) {
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
        Optional<Customer> entity = customerService.getCustomerById(id);
        return entity.isPresent() ? customerMapper.toDto(entity.get()) : null;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    public List<CustomerDto> getCustomerByFullName(String fullName) {
        return customerService.getCustomerByFullName(fullName)
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }


}
