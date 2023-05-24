package groupflow.controller;

import groupflow.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/weather")
@Slf4j
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    @GetMapping("")
    public String getWeather(){

        String result=weatherService.getWeather();
       return result;

    }
}
