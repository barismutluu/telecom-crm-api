package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.TariffRequest;
import com.barismutlu.telecomcrm.model.Tariff;
import com.barismutlu.telecomcrm.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffService {

    private final TariffRepository tariffRepository;

    public Tariff createTariff(TariffRequest request) {

        Tariff t = new Tariff();
        t.setName(request.getName());
        t.setPrice(request.getPrice());
        t.setInternetGB(request.getInternetGB());
        t.setMinutes(request.getMinutes());
        t.setSms(request.getSms());

        Tariff savedTariff = tariffRepository.save(t);
        log.info("Tariff created. packageId={} name={}", savedTariff.getId(), savedTariff.getName());
        return savedTariff;
    }

    public List<Tariff> getAllPackages() {
        return tariffRepository.findAll();
    }
    public Tariff getById(Long id) {
        return tariffRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tariff lookup failed. packageId={}", id);
                    return new RuntimeException("Package not found");
                });
    }
}
