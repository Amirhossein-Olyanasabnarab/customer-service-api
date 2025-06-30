package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDAO;
import dk.dev.app.model.Customer;
import dk.dev.app.model.LegalCustomer;
import dk.dev.app.model.RealCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@Primary
public class CustomerH2Dao implements CustomerDAO {


    private final JdbcTemplate jdbc;
    @Autowired
    public CustomerH2Dao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Customer save(Customer customer) {
        String customerSql = "INSERT INTO customer (full_name, phone_number, email, type) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(customerSql, new String[]{"id"});
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getPhoneNumber());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getType().name());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        customer.setId(id);

        if (customer instanceof RealCustomer realCustomer) {
            String realCustomerSql = "INSERT INTO real_customer (id, nationality, age) VALUES (?, ?, ?)";
            jdbc.update(realCustomerSql, id, realCustomer.getNationality(), realCustomer.getAge());
        } else if (customer instanceof LegalCustomer legalCustomer) {
            String legalCustomerSql = "INSERT INTO legal_customer (id, company_name, industry) VALUES (?, ?, ?)";
            jdbc.update(legalCustomerSql, id, legalCustomer.getCompanyName(), legalCustomer.getIndustry());
        }

        return customer;
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
