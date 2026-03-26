package com.barismutlu.telecomcrm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupportRequestCreateRequest {

    private String title;
    private String description;
    private Long customerId;
}
