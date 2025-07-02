package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDAO;
import dk.dev.app.enums.CustomerType;
import dk.dev.app.model.Customer;
import dk.dev.app.model.LegalCustomer;
import dk.dev.app.model.RealCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
//@Primary
public class CustomerJdbcDao implements CustomerDAO {


    private final JdbcTemplate jdbc;

    @Autowired
    public CustomerJdbcDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Customer save(Customer customer) {
        if (existsById(customer.getId())) {
            return update(customer);
        } else {
            return insert(customer);
        }
    }


    private Customer insert(Customer customer) {
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


    private Customer update(Customer customer) {
        String customerSql = "UPDATE customer SET full_name = ?, phone_number = ?, email = ?, type = ? WHERE id = ?";
        jdbc.update(customerSql, customer.getFullName(), customer.getPhoneNumber(), customer.getEmail(),
                customer.getType().name(), customer.getId());

        if (customer instanceof RealCustomer realCustomer) {
            String realCustomerSql = "UPDATE real_customer SET nationality = ?, age = ? WHERE id = ?";
            jdbc.update(realCustomerSql, realCustomer.getNationality(), realCustomer.getAge(), customer.getId());
        } else if (customer instanceof LegalCustomer legalCustomer) {
            String legalCustomerSql = "UPDATE legal_customer SET company_name = ?, industry = ? WHERE id = ?";
            jdbc.update(legalCustomerSql, legalCustomer.getCompanyName(), legalCustomer.getIndustry(), customer.getId());
        }
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        String customerSql = "DELETE FROM customer WHERE id = ?";
        jdbc.update(customerSql, id);
    }

    @Override
    public Optional<Customer> findById(Long id) {

        if (!existsById(id)) {
            return Optional.empty();
        }

        String customerSql = "SELECT * FROM customer WHERE id = ?";
        Map<String, Object> customerRow = jdbc.queryForMap(customerSql, id);

        CustomerType type = CustomerType.valueOf((String) customerRow.get("type"));
        Customer customer;

        if (type == CustomerType.REAL) {
            String realCustomerSql = "SELECT * FROM real_customer WHERE id = ?";
            Map<String, Object> realCustomerRow = jdbc.queryForMap(realCustomerSql, id);
            customer = RealCustomer.builder()
                    .id(id)
                    .fullName((String) customerRow.get("full_name"))
                    .phoneNumber((String) customerRow.get("phone_number"))
                    .email((String) customerRow.get("email"))
                    .type(type)
                    .nationality((String) realCustomerRow.get("nationality"))
                    .age((String) realCustomerRow.get("age"))
                    .build();
        } else {
            String legalCustomerSql = "SELECT * FROM legal_customer WHERE id = ?";
            Map<String, Object> legalCustomerRow = jdbc.queryForMap(legalCustomerSql, id);
            customer = LegalCustomer.builder()
                    .id(id)
                    .fullName((String) customerRow.get("full_name"))
                    .phoneNumber((String) customerRow.get("phone_number"))
                    .email((String) customerRow.get("email"))
                    .type(type)
                    .companyName((String) legalCustomerRow.get("company_name"))
                    .industry((String) legalCustomerRow.get("industry"))
                    .build();
        }

        return Optional.of(customer);
    }

    @Override
    public List<Customer> findAll() {
        String customerSql = "SELECT * FROM customer";
        return jdbc.query(customerSql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            CustomerType type = CustomerType.valueOf(rs.getString("type"));

            if (type == CustomerType.REAL) {
                String realCustomerSql = "SELECT * FROM real_customer WHERE id = ?";
                Map<String, Object> realCustomerRow = jdbc.queryForMap(realCustomerSql, id);
                return RealCustomer.builder()
                        .id(id)
                        .fullName(rs.getString("full_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .email(rs.getString("email"))
                        .type(type)
                        .nationality((String) realCustomerRow.get("nationality"))
                        .age((String) realCustomerRow.get("age"))
                        .build();
            } else {
                String legalCustomerSql = "SELECT * FROM legal_customer WHERE id = ?";
                Map<String, Object> legalCustomerRow = jdbc.queryForMap(legalCustomerSql, id);
                return LegalCustomer.builder()
                        .id(id)
                        .fullName(rs.getString("full_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .email(rs.getString("email"))
                        .type(type)
                        .companyName((String) legalCustomerRow.get("company_name"))
                        .industry((String) legalCustomerRow.get("industry"))
                        .build();
            }
        });
    }

    @Override
    public boolean existsById(Long id) {
        String customerSql = "SELECT COUNT(*) FROM customer WHERE id = ?";
        Integer count = jdbc.queryForObject(customerSql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public List<Customer> findByName(String name) {
        String customerSql = "SELECT * FROM customer WHERE LOWER(name) = LOWER(?)";
        return jdbc.query(customerSql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            CustomerType type = CustomerType.valueOf(rs.getString("type"));

            if (type == CustomerType.REAL) {
                String realCustomerSql = "SELECT * FROM real_customer WHERE id = ?";
                Map<String, Object> realCustomerRow = jdbc.queryForMap(realCustomerSql, id);
                return RealCustomer.builder()
                        .id(id)
                        .fullName(rs.getString("full_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .email(rs.getString("email"))
                        .type(type)
                        .nationality((String) realCustomerRow.get("nationality"))
                        .age((String) realCustomerRow.get("age"))
                        .build();
            } else {
                String legalCustomerSql = "SELECT * FROM legal_customer WHERE id = ?";
                Map<String, Object> legalCustomerRow = jdbc.queryForMap(legalCustomerSql, id);
                return LegalCustomer.builder()
                        .id(id)
                        .fullName(rs.getString("full_name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .email(rs.getString("email"))
                        .type(type)
                        .companyName((String) legalCustomerRow.get("company_name"))
                        .industry((String) legalCustomerRow.get("industry"))
                        .build();
            }
        }, name);
    }
}
