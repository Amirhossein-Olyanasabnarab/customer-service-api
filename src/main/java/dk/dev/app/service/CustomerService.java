package dk.dev.app.service;

import dk.dev.app.dao.CustomerDAO;
import dk.dev.app.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Customer addCustomer(Customer customer) {
        return customerDAO. save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        if (customerDAO.existsById(id)) {
            customer.setId(id);
            return customerDAO.save(customer);
        }else
            return null;
    }

    public boolean deleteCustomer(Long id) {
        if (customerDAO.existsById(id)) {
            customerDAO.deleteById(id);
            return true;
        }else
            return false;
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerDAO.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public List<Customer> getCustomerByFullName(String fullName) {
        return customerDAO.findByFullName(fullName);
    }

 }
