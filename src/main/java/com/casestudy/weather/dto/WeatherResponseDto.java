package com.casestudy.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WeatherResponseDto {
    String cod;
    String message;

    @JsonProperty("list")
    List<Tempreatue> temperature;
}
