package com.casestudy.weather.service;

import com.casestudy.weather.dto.CountyZipDto;
import com.casestudy.weather.dto.Tempreatue;
import com.casestudy.weather.dto.WeatherResponseDto;
import com.casestudy.weather.utils.WeatherPredictionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WeatherPredictionService {
    private String cvsSplitBy = ",";

    @Value("${csv.file}")
    private String csvFile;
    @Value("${weather.prediction.token}")
    private String weatherAPIToken;

    @Value("${weather.prediction.url}")
    private String weatherAPI;

    private RestTemplate restTemplate = new RestTemplate();

    public String generateWeatherReport() {
        try (BufferedWriter outputfile = new BufferedWriter(new FileWriter(""))) {
            // adding header to csv

            outputfile.write("Zip Code");
            outputfile.write(",");
            outputfile.write("Country Code");
            outputfile.write(",");
            outputfile.write("Date");
            outputfile.write(",");
            outputfile.write("Min");
            outputfile.write(",");
            outputfile.write("Max");
            outputfile.newLine();

            List<Double> minMaxTemperature = new ArrayList<>();
            List<CountyZipDto> collect = new BufferedReader(new FileReader(csvFile))
                    .lines()
                    .skip(1)
                    .map(mapToItem)
                    .collect(Collectors.toList());
            collect.stream().forEach(s -> {
                        HttpEntity<Object> httpEntity = WeatherPredictionUtils.headersData();
                        UriComponents builder = UriComponentsBuilder.fromHttpUrl(weatherAPI)
                                .queryParam("APPID", weatherAPIToken)
                                .queryParam("zip", s.getZipCode().replaceAll("\"", "") + "," + s.getCountyCode().replaceAll("\"", ""))
                                .build();
                        try {
                            WeatherResponseDto body = restTemplate.exchange(builder.toUri(), HttpMethod.GET, httpEntity, WeatherResponseDto.class).getBody();
                            List<Tempreatue> temperature = body.getTemperature();
                            Set<String> dates = new TreeSet<>();
                            for (Tempreatue t : temperature) {
                                String[] s1 = t.getDate().split(" ");
                                String date = s1[0];
                                dates.add(date);
                            }
                            for (String dt : dates) {
                                for (Tempreatue temp : temperature) {
                                    String[] date2 = temp.getDate().split(" ");
                                    if (dt.equalsIgnoreCase(date2[0])) {
                                        minMaxTemperature.add(temp.getMinMaxTempreatue().getTemp_min());
                                        minMaxTemperature.add(temp.getMinMaxTempreatue().getTemp_max());
                                    }
                                }
                                System.out.println(minMaxTemperature.get(0));
                                System.out.println(minMaxTemperature.get(minMaxTemperature.size() - 1));
                                outputfile.write(s.getZipCode().replaceAll("\"", ""));
                                outputfile.write(",");
                                outputfile.write(s.getCountyCode().replaceAll("\"", ""));
                                outputfile.write(",");
                                outputfile.write(dt);
                                outputfile.write(",");
                                outputfile.write(String.valueOf(minMaxTemperature.get(0)));
                                outputfile.write(",");
                                outputfile.write(String.valueOf(minMaxTemperature.get(minMaxTemperature.size() - 1)));
                                outputfile.newLine();
                                minMaxTemperature.clear();
                            }
                        } catch (Exception e) {
                            try {
                                outputfile.write(s.getZipCode().replaceAll("\"", ""));
                                outputfile.write(",");
                                outputfile.write(s.getCountyCode().replaceAll("\"", ""));
                                outputfile.write(",");
                                outputfile.write("");
                                outputfile.write(",");
                                outputfile.write("");
                                outputfile.write(",");
                                outputfile.write("");
                                outputfile.newLine();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    private Function<String, CountyZipDto> mapToItem = (line) -> {
        String code[] = line.split(cvsSplitBy);
        CountyZipDto countyZipDto1 = new CountyZipDto();
        countyZipDto1.setZipCode(code[0]);
        countyZipDto1.setCountyCode(code[1]);
        return countyZipDto1;
    };
}
