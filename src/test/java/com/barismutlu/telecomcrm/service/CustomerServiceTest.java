package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.CustomerRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer_shouldSaveCustomer_whenEmailAndPhoneAreUnique() {
        CustomerRequest request = customerRequest();

        when(customerRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByPhoneNumber(request.getPhoneNumber())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer result = customerService.createCustomer(request);

        assertThat(result.getName()).isEqualTo("Baris");
        assertThat(result.getSurname()).isEqualTo("Mutlu");
        assertThat(result.getEmail()).isEqualTo("baris@example.com");
        assertThat(result.getPhoneNumber()).isEqualTo("5551112233");

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());
        assertThat(customerCaptor.getValue().getEmail()).isEqualTo("baris@example.com");
    }

    @Test
    void createCustomer_shouldThrowException_whenEmailAlreadyExists() {
        CustomerRequest request = customerRequest();
        when(customerRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Customer()));

        assertThatThrownBy(() -> customerService.createCustomer(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email already exists");

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer_shouldThrowException_whenPhoneAlreadyExists() {
        CustomerRequest request = customerRequest();
        when(customerRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByPhoneNumber(request.getPhoneNumber())).thenReturn(Optional.of(new Customer()));

        assertThatThrownBy(() -> customerService.createCustomer(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Phone already exists");

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void getCustomerById_shouldThrowException_whenCustomerDoesNotExist() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Customer not found");
    }

    private CustomerRequest customerRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setName("Baris");
        request.setSurname("Mutlu");
        request.setEmail("baris@example.com");
        request.setPhoneNumber("5551112233");
        return request;
    }
}
