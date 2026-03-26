package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.SupportRequestCreateRequest;
import com.barismutlu.telecomcrm.model.Customer;
import com.barismutlu.telecomcrm.model.SupportRequest;
import com.barismutlu.telecomcrm.repository.SupportRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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

        return supportRequestRepository.save(sr);
    }

    public List<SupportRequest> getAll() {
        return supportRequestRepository.findAll();
    }

    public SupportRequest closeRequest(Long id) {
        SupportRequest sr = supportRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        sr.setStatus("CLOSED");

        return supportRequestRepository.save(sr);
    }
}
