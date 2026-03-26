package com.barismutlu.telecomcrm.controller;

import com.barismutlu.telecomcrm.dto.AssignPackageRequest;
import com.barismutlu.telecomcrm.model.SubscriptionPackage;
import com.barismutlu.telecomcrm.service.SubscriptionPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription-packages")
@RequiredArgsConstructor
public class SubscriptionPackageController {

    private final SubscriptionPackageService service;

    @PostMapping
    public SubscriptionPackage assign(@RequestBody AssignPackageRequest request) {
        return service.assignPackage(request);
    }
}
