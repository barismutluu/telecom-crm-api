package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.TariffRequest;
import com.barismutlu.telecomcrm.model.Tariff;
import com.barismutlu.telecomcrm.repository.TariffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TariffServiceTest {

    @Mock
    private TariffRepository tariffRepository;

    @InjectMocks
    private TariffService tariffService;

    @Test
    void createTariff_shouldSaveTariff() {
        TariffRequest request = tariffRequest();
        when(tariffRepository.save(any(Tariff.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tariff result = tariffService.createTariff(request);

        assertThat(result.getName()).isEqualTo("Mega 20GB");
        assertThat(result.getPrice()).isEqualTo(250.0);
        assertThat(result.getInternetGB()).isEqualTo(20);
        assertThat(result.getMinutes()).isEqualTo(1000);
        assertThat(result.getSms()).isEqualTo(250);
    }

    @Test
    void getAllPackages_shouldReturnTariffs() {
        Tariff tariff = new Tariff();
        tariff.setName("Mega 20GB");
        when(tariffRepository.findAll()).thenReturn(List.of(tariff));

        List<Tariff> result = tariffService.getAllPackages();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Mega 20GB");
    }

    @Test
    void getById_shouldThrowException_whenPackageDoesNotExist() {
        when(tariffRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tariffService.getById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Package not found");
    }

    private TariffRequest tariffRequest() {
        TariffRequest request = new TariffRequest();
        request.setName("Mega 20GB");
        request.setPrice(250.0);
        request.setInternetGB(20);
        request.setMinutes(1000);
        request.setSms(250);
        return request;
    }
}
