package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.CustomerRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(CustomerRequest request) {


        customerRepository.findByEmail(request.getEmail())
                .ifPresent(c -> {
                    log.warn("Customer creation failed. Email already exists. email={}", request.getEmail());
                    throw new RuntimeException("Email already exists");
                });

        customerRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(c -> {
                    log.warn("Customer creation failed. Phone already exists. phoneNumber={}", request.getPhoneNumber());
                    throw new RuntimeException("Phone already exists");
                });

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created. customerId={} email={}", savedCustomer.getId(), savedCustomer.getEmail());
        return savedCustomer;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer lookup failed. customerId={}", id);
                    return new RuntimeException("Customer not found");
                });
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer lookup failed. customerId={}", id);
                    return new RuntimeException("Customer not found");
                });
    }
}
