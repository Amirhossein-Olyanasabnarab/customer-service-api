package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDAO;
import dk.dev.app.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository

public class CustomerInMemoryDao implements CustomerDAO {


    private final AtomicLong customerId = new AtomicLong(0);
    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer customer) {
        if (!existsById(customer.getId())) {
            customer.setId(customerId.incrementAndGet());
        }
        customers.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        customers.remove(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        if (!existsById(id)) {
            return Optional.empty();
        }
        return Optional.of(customers.get(id));
    }

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return customers.containsKey(id);
    }

    @Override
    public List<Customer> findByName(String name) {
        return customers.values().stream()
                .filter(customer -> customer.getFullName() != null &&
                        customer.getFullName().equalsIgnoreCase(name))
                .toList();
    }
}
