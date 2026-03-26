package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.SubscriptionRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.model.Subscription;
import com.barismutlu.telecomcrm.repository.CustomerRepository;
import com.barismutlu.telecomcrm.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;

    public Subscription createSubscription(SubscriptionRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Subscription subscription = new Subscription();
        subscription.setPhoneNumber(request.getPhoneNumber());
        subscription.setStatus("ACTIVE");
        subscription.setCustomer(customer);

        return subscriptionRepository.save(subscription);
    }
    public Subscription getById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
    }
    public boolean existsById(Long id) {
        return subscriptionRepository.existsById(id);
    }
}
