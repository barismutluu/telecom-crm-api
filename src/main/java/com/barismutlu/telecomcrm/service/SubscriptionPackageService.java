package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.AssignPackageRequest;
import com.barismutlu.telecomcrm.model.Subscription;
import com.barismutlu.telecomcrm.model.SubscriptionPackage;
import com.barismutlu.telecomcrm.model.Tariff;
import com.barismutlu.telecomcrm.repository.SubscriptionPackageRepository;
import com.barismutlu.telecomcrm.repository.SubscriptionRepository;
import com.barismutlu.telecomcrm.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionPackageService {

    private final SubscriptionService subscriptionService;
    private final TariffService tariffService;
    private final SubscriptionPackageRepository subscriptionPackageRepository;

    public SubscriptionPackage assignPackage(AssignPackageRequest request) {

        Subscription subscription = subscriptionService.getById(request.getSubscriptionId());

        Tariff tariff = tariffService.getById(request.getPackageId());


        subscriptionPackageRepository
                .findBySubscriptionIdAndEndDateIsNull(request.getSubscriptionId())
                .ifPresent(active -> {
                    active.setEndDate(LocalDateTime.now());
                    subscriptionPackageRepository.save(active);
                });


        SubscriptionPackage sp = new SubscriptionPackage();
        sp.setSubscription(subscription);
        sp.setTariff(tariff);
        sp.setStartDate(LocalDateTime.now());

        return subscriptionPackageRepository.save(sp);
    }
}