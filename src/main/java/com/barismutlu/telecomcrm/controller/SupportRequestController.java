package com.barismutlu.telecomcrm.controller;

import com.barismutlu.telecomcrm.dto.SupportRequestCreateRequest;
import com.barismutlu.telecomcrm.model.SupportRequest;
import com.barismutlu.telecomcrm.service.SupportRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support-requests")
@RequiredArgsConstructor
public class SupportRequestController {

    private final SupportRequestService service;

    @PostMapping
    public SupportRequest create(@RequestBody SupportRequestCreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<SupportRequest> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}/close")
    public SupportRequest close(@PathVariable Long id) {
        return service.closeRequest(id);
    }
}
