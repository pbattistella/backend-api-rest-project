package br.com.pbattistella.project.service;

import br.com.pbattistella.project.model.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> findAll();
    public Customer findById(Long id);
    public Customer create(Customer customer);
    public Customer update(Long id, Customer customer);
    public void delete(Long id);

}
