package com.barismutlu.telecomcrm.repository;

import com.barismutlu.telecomcrm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer>findByPhoneNumber(String phoneNumber);

}
