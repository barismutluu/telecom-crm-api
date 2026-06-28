package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.SupportRequestCreateRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.model.SupportRequest;
import com.barismutlu.telecomcrm.repository.SupportRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupportRequestServiceTest {

    @Mock
    private SupportRequestRepository supportRequestRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private SupportRequestService supportRequestService;

    @Test
    void create_shouldSaveOpenSupportRequest() {
        Customer customer = new Customer();
        customer.setId(1L);
        SupportRequestCreateRequest request = supportRequestCreateRequest();

        when(customerService.getById(1L)).thenReturn(customer);
        when(supportRequestRepository.save(any(SupportRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SupportRequest result = supportRequestService.create(request);

        assertThat(result.getTitle()).isEqualTo("Line issue");
        assertThat(result.getDescription()).isEqualTo("Internet connection is unstable");
        assertThat(result.getStatus()).isEqualTo("OPEN");
        assertThat(result.getCustomer()).isSameAs(customer);
    }

    @Test
    void getAll_shouldReturnSupportRequests() {
        SupportRequest supportRequest = new SupportRequest();
        supportRequest.setTitle("Line issue");
        when(supportRequestRepository.findAll()).thenReturn(List.of(supportRequest));

        List<SupportRequest> result = supportRequestService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Line issue");
    }

    @Test
    void closeRequest_shouldSetStatusClosed_whenRequestExists() {
        SupportRequest supportRequest = new SupportRequest();
        supportRequest.setStatus("OPEN");

        when(supportRequestRepository.findById(1L)).thenReturn(Optional.of(supportRequest));
        when(supportRequestRepository.save(any(SupportRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SupportRequest result = supportRequestService.closeRequest(1L);

        assertThat(result.getStatus()).isEqualTo("CLOSED");
    }

    @Test
    void closeRequest_shouldThrowException_whenRequestDoesNotExist() {
        when(supportRequestRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> supportRequestService.closeRequest(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Request not found");
    }

    private SupportRequestCreateRequest supportRequestCreateRequest() {
        SupportRequestCreateRequest request = new SupportRequestCreateRequest();
        request.setCustomerId(1L);
        request.setTitle("Line issue");
        request.setDescription("Internet connection is unstable");
        return request;
    }
}
