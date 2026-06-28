package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.AssignPackageRequest;
import com.barismutlu.telecomcrm.model.Subscription;
import com.barismutlu.telecomcrm.model.SubscriptionPackage;
import com.barismutlu.telecomcrm.model.Tariff;
import com.barismutlu.telecomcrm.repository.SubscriptionPackageRepository;
import com.barismutlu.telecomcrm.repository.SubscriptionRepository;
import com.barismutlu.telecomcrm.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
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
                    log.info("Active subscription package closed. subscriptionPackageId={} subscriptionId={}",
                            active.getId(),
                            request.getSubscriptionId());
                });


        SubscriptionPackage sp = new SubscriptionPackage();
        sp.setSubscription(subscription);
        sp.setTariff(tariff);
        sp.setStartDate(LocalDateTime.now());

        SubscriptionPackage savedPackage = subscriptionPackageRepository.save(sp);
        log.info("Package assigned to subscription. subscriptionPackageId={} subscriptionId={} packageId={}",
                savedPackage.getId(),
                request.getSubscriptionId(),
                request.getPackageId());
        return savedPackage;
    }
}
