package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.SubscriptionRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.model.Subscription;
import com.barismutlu.telecomcrm.repository.CustomerRepository;
import com.barismutlu.telecomcrm.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;

    public Subscription createSubscription(SubscriptionRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> {
                    log.warn("Subscription creation failed. Customer not found. customerId={}", request.getCustomerId());
                    return new RuntimeException("Customer not found");
                });

        Subscription subscription = new Subscription();
        subscription.setPhoneNumber(request.getPhoneNumber());
        subscription.setStatus("ACTIVE");
        subscription.setCustomer(customer);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        log.info("Subscription created. subscriptionId={} customerId={} phoneNumber={}",
                savedSubscription.getId(),
                customer.getId(),
                savedSubscription.getPhoneNumber());
        return savedSubscription;
    }
    public Subscription getById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Subscription lookup failed. subscriptionId={}", id);
                    return new RuntimeException("Subscription not found");
                });
    }
    public boolean existsById(Long id) {
        return subscriptionRepository.existsById(id);
    }
}
