package com.casestudy.weather.controller;

import com.casestudy.weather.service.WeatherPredictionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherPredictionController {
    private static Logger logger = LoggerFactory.getLogger(WeatherPredictionController.class);

    private WeatherPredictionService weatherPredictionService;

    @Autowired
    public void setWeatherPredictionService(WeatherPredictionService weatherPredictionService) {
        this.weatherPredictionService = weatherPredictionService;
    }

    @GetMapping
    public ResponseEntity<Object> getWeatherPrediction() {
        return ResponseEntity.ok().body(weatherPredictionService.generateWeatherReport());

    }

}
