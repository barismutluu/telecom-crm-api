package com.barismutlu.telecomcrm.repository;

import com.barismutlu.telecomcrm.model.SubscriptionPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackage, Long> {
    Optional<SubscriptionPackage> findBySubscriptionIdAndEndDateIsNull(Long subscriptionId);
}