package com.casestudy.weather.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class WeatherPredictionUtils {

    public static HttpEntity<Object> headersData() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        return new HttpEntity<>(httpHeaders);
    }
}
