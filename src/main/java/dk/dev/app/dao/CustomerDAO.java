package dk.dev.app.dao;

import dk.dev.app.model.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer save(Customer customer);
    void delete(Long id);
    Customer findById(Long id);
    List<Customer> findAll();
    boolean existsById(Long id);
}
