package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDAO;
import dk.dev.app.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository

public class CustomerInMemoryDao implements CustomerDAO {


    private final AtomicLong customerId = new AtomicLong(0);
    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer customer) {
        Long id = customerId.incrementAndGet();
        customer.setId(id);
        customers.put(id, customer);
        return customer;
    }

    @Override
    public Customer update(Long id, Customer customer) {
        customer.setId(id);
        customers.put(id, customer);
        return customer;
    }

    @Override
    public boolean delete(Long id) {
        return customers.remove(id) != null;
    }

    @Override
    public Customer findById(Long id) {
        return customers.get(id);
    }

    @Override
    public List<Customer> findAll() {
        return customers.values().stream().toList();
    }

    @Override
    public boolean existsById(Long id) {
        return customers.containsKey(id);
    }
}
