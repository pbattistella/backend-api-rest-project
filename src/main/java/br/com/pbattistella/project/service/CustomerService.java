package br.com.pbattistella.project.service;

import br.com.pbattistella.project.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    public Page<Customer> findAll(Pageable pageable);
    public Customer findById(Long id);
    public Customer create(Customer customer);
    public Customer update(Long id, Customer customer);
    public void delete(Long id);

}
