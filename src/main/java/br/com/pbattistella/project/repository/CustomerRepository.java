package br.com.pbattistella.project.repository;

import br.com.pbattistella.project.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
