package com.barismutlu.telecomcrm.controller;

import com.barismutlu.telecomcrm.dto.SubscriptionRequest;
import com.barismutlu.telecomcrm.model.Subscription;
import com.barismutlu.telecomcrm.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public Subscription create(@RequestBody SubscriptionRequest request) {
        return subscriptionService.createSubscription(request);
    }
}
