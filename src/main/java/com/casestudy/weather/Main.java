package com.casestudy.weather;

import com.casestudy.weather.service.WeatherPredictionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    public static WeatherPredictionService weatherPredictionService;
    public static void main(String ... args){
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class,args);
        weatherPredictionService = applicationContext.getBean(WeatherPredictionService.class);
        weatherPredictionService.generateWeatherReport();
        applicationContext.close();
    }
}
