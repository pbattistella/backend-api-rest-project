package br.com.pbattistella.project.controller;

import br.com.pbattistella.project.model.Customer;
import br.com.pbattistella.project.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer", description = "Endpoints for managing customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/")
    @Operation(summary = "Finds all customers", description = "Finds all customers", tags = {"Customer"})
    public Page<Customer> findAll(@PageableDefault(sort = {"fullName"}) Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find one customer", description = "Find one customer", tags = {"Customer"})
    public Customer findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Create one customer", description = "Create one customer", tags = {"Customer"})
    public Customer create(@RequestBody Customer customer) throws Exception {
        return service.create(customer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updating one customer", description = "Updating one customer", tags = {"Customer"})
    public Customer update(@PathVariable(value = "id") Long id, @RequestBody Customer customer) throws Exception {
        return service.update(id, customer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting one customer", description = "Deleting one customer", tags = {"Customer"})
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
