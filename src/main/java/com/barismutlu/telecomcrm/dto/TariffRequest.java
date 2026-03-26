package com.barismutlu.telecomcrm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TariffRequest {

    private String name;
    private Double price;
    private Integer internetGB;
    private Integer minutes;
    private Integer sms;
}
