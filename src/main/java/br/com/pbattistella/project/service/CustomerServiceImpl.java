package br.com.pbattistella.project.service;

import br.com.pbattistella.project.exception.ResourceNotFoundException;
import br.com.pbattistella.project.model.Customer;
import br.com.pbattistella.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

    @Autowired
    private CustomerRepository repository;

    @Override
    public List<Customer> findAll() {
        logger.info("Finding all customers!");
        return repository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        logger.info("Finding one customer!");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
    }

    @Override
    public Customer create(Customer customer) {
        logger.info("Creating one customer!");
        return repository.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        logger.info("Updating one customer!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        entity.setFullName(customer.getFullName());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setBirthDate(customer.getBirthDate());
        entity.setGender(customer.getGender());

        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting one customer!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
        repository.delete(entity);

    }
}
