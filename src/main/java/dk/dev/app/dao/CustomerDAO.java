package dk.dev.app.dao;

import dk.dev.app.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    Customer save(Customer customer);
    void deleteById(Long id);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    boolean existsById(Long id);
    List<Customer> findByFullName(String fullName);

}
