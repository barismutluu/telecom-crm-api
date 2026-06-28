package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.dto.AssignPackageRequest;
import com.barismutlu.telecomcrm.model.Subscription;
import com.barismutlu.telecomcrm.model.SubscriptionPackage;
import com.barismutlu.telecomcrm.model.Tariff;
import com.barismutlu.telecomcrm.repository.SubscriptionPackageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionPackageServiceTest {

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private TariffService tariffService;

    @Mock
    private SubscriptionPackageRepository subscriptionPackageRepository;

    @InjectMocks
    private SubscriptionPackageService subscriptionPackageService;

    @Test
    void assignPackage_shouldSaveNewPackage_whenNoActivePackageExists() {
        AssignPackageRequest request = assignPackageRequest();
        Subscription subscription = new Subscription();
        Tariff tariff = new Tariff();

        when(subscriptionService.getById(1L)).thenReturn(subscription);
        when(tariffService.getById(2L)).thenReturn(tariff);
        when(subscriptionPackageRepository.findBySubscriptionIdAndEndDateIsNull(1L)).thenReturn(Optional.empty());
        when(subscriptionPackageRepository.save(any(SubscriptionPackage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SubscriptionPackage result = subscriptionPackageService.assignPackage(request);

        assertThat(result.getSubscription()).isSameAs(subscription);
        assertThat(result.getTariff()).isSameAs(tariff);
        assertThat(result.getStartDate()).isNotNull();
        assertThat(result.getEndDate()).isNull();
    }

    @Test
    void assignPackage_shouldCloseActivePackageAndSaveNewPackage_whenActivePackageExists() {
        AssignPackageRequest request = assignPackageRequest();
        Subscription subscription = new Subscription();
        Tariff tariff = new Tariff();
        SubscriptionPackage activePackage = new SubscriptionPackage();

        when(subscriptionService.getById(1L)).thenReturn(subscription);
        when(tariffService.getById(2L)).thenReturn(tariff);
        when(subscriptionPackageRepository.findBySubscriptionIdAndEndDateIsNull(1L))
                .thenReturn(Optional.of(activePackage));
        when(subscriptionPackageRepository.save(any(SubscriptionPackage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SubscriptionPackage result = subscriptionPackageService.assignPackage(request);

        assertThat(activePackage.getEndDate()).isNotNull();
        assertThat(result.getSubscription()).isSameAs(subscription);
        assertThat(result.getTariff()).isSameAs(tariff);

        ArgumentCaptor<SubscriptionPackage> captor = ArgumentCaptor.forClass(SubscriptionPackage.class);
        verify(subscriptionPackageRepository).save(activePackage);
        verify(subscriptionPackageRepository, times(2)).save(captor.capture());
        assertThat(captor.getAllValues().get(1).getStartDate()).isNotNull();
    }

    private AssignPackageRequest assignPackageRequest() {
        AssignPackageRequest request = new AssignPackageRequest();
        request.setSubscriptionId(1L);
        request.setPackageId(2L);
        return request;
    }
}
