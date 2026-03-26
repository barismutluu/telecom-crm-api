package com.barismutlu.telecomcrm.repository;

import com.barismutlu.telecomcrm.model.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, Long> {
}
