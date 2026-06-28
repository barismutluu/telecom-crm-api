package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.SubscriptionRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.model.Subscription;
import com.barismutlu.telecomcrm.repository.CustomerRepository;
import com.barismutlu.telecomcrm.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void createSubscription_shouldSaveActiveSubscription_whenCustomerExists() {
        Customer customer = new Customer();
        customer.setId(1L);

        SubscriptionRequest request = subscriptionRequest();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Subscription result = subscriptionService.createSubscription(request);

        assertThat(result.getPhoneNumber()).isEqualTo("5552223344");
        assertThat(result.getStatus()).isEqualTo("ACTIVE");
        assertThat(result.getCustomer()).isSameAs(customer);
    }

    @Test
    void createSubscription_shouldThrowException_whenCustomerDoesNotExist() {
        SubscriptionRequest request = subscriptionRequest();
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subscriptionService.createSubscription(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Customer not found");

        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void getById_shouldThrowException_whenSubscriptionDoesNotExist() {
        when(subscriptionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subscriptionService.getById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Subscription not found");
    }

    private SubscriptionRequest subscriptionRequest() {
        SubscriptionRequest request = new SubscriptionRequest();
        request.setCustomerId(1L);
        request.setPhoneNumber("5552223344");
        return request;
    }
}
