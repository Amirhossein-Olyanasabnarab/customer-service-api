package dk.dev.app.dao.impl;

import dk.dev.app.dao.CustomerDAO;
import dk.dev.app.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
@Primary
public interface CustomerJpaDao extends JpaRepository<Customer, Long>, CustomerDAO {
}
