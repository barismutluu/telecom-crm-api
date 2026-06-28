package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.SupportRequestCreateRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.model.SupportRequest;
import com.barismutlu.telecomcrm.repository.SupportRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupportRequestService {

    private final SupportRequestRepository supportRequestRepository;
    private final CustomerService customerService;

    public SupportRequest create(SupportRequestCreateRequest request) {

        Customer customer = customerService.getById(request.getCustomerId());

        SupportRequest sr = new SupportRequest();
        sr.setTitle(request.getTitle());
        sr.setDescription(request.getDescription());
        sr.setStatus("OPEN");
        sr.setCustomer(customer);

        SupportRequest savedRequest = supportRequestRepository.save(sr);
        log.info("Support request created. supportRequestId={} customerId={}",
                savedRequest.getId(),
                customer.getId());
        return savedRequest;
    }

    public List<SupportRequest> getAll() {
        return supportRequestRepository.findAll();
    }

    public SupportRequest closeRequest(Long id) {
        SupportRequest sr = supportRequestRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Support request lookup failed. supportRequestId={}", id);
                    return new RuntimeException("Request not found");
                });

        sr.setStatus("CLOSED");

        SupportRequest savedRequest = supportRequestRepository.save(sr);
        log.info("Support request closed. supportRequestId={}", savedRequest.getId());
        return savedRequest;
    }
}
