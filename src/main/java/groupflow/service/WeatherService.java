package groupflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class WeatherService {

    @Transactional
    public String getWeather(){
    log.info("getWeather service");
        return  null;
    }


}
