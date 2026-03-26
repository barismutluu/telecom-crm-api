package com.barismutlu.telecomcrm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequest {

    private String phoneNumber;
    private Long customerId;
}
