package com.barismutlu.telecomcrm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignPackageRequest {

    private Long subscriptionId;
    private Long packageId;
}