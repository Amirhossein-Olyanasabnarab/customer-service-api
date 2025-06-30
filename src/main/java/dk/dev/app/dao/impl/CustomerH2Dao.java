package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDAO;
import dk.dev.app.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerH2Dao implements CustomerDAO {


    private final JdbcTemplate jdbc;
    @Autowired
    public CustomerH2Dao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public Customer update(Long id, Customer customer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Customer findById(Long id) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
