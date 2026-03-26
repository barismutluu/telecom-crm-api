package com.barismutlu.telecomcrm.controller;

import com.barismutlu.telecomcrm.dto.TariffRequest;
import com.barismutlu.telecomcrm.model.Tariff;
import com.barismutlu.telecomcrm.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    @PostMapping
    public Tariff create(@RequestBody TariffRequest request) {
        return tariffService.createTariff(request);
    }

    @GetMapping
    public List<Tariff> getAll() {
        return tariffService.getAllPackages();
    }
}
