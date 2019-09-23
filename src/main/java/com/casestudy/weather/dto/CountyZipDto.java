package com.casestudy.weather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class CountyZipDto {
    private String zipCode;
    private String countyCode;
}
