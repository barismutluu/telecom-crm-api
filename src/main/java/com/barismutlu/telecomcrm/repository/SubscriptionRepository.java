package com.barismutlu.telecomcrm.repository;

import com.barismutlu.telecomcrm.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
