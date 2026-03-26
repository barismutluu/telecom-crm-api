package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.CustomerRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(CustomerRequest request) {


        customerRepository.findByEmail(request.getEmail())
                .ifPresent(c -> {
                    throw new RuntimeException("Email already exists");
                });

        customerRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(c -> {
                    throw new RuntimeException("Phone already exists");
                });

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}