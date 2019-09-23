package com.casestudy.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Tempreatue {

    @JsonProperty("main")
    private MinMaxTempreatue minMaxTempreatue;

    @JsonProperty("dt_txt")
    private String date;

    @Getter
    @Setter
    @ToString
    public static class MinMaxTempreatue {

        @JsonProperty("temp_min")
        private Double temp_min;

        @JsonProperty("temp_max")
        private Double temp_max;
    }
}