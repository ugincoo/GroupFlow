package groupflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Slf4j
public class WeatherService {

    @Transactional
    public String getWeather() {

        String jsonResponse = null;
        try {
            URL url = new URL("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=u5GGNGc4Ou8WiOpmvx5WO4SIYcfZg3WGVQ38D%2BD%2FwOqo5tFeIUu%2BTnvzlwJdpKmw7S3LZqHbSJRiQtr7pPUh6g%3D%3D&pageNo=1&numOfRows=20&dataType=JSON&base_date=20230524&base_time=0600&nx=58&ny=121");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                jsonResponse = response.toString();
                log.info("jsonResponse??:" + jsonResponse);


                // 예시: JSON 문자열을 객체로 변환
                // ObjectMapper mapper = new ObjectMapper();
                // MyObject myObject = mapper.readValue(jsonResponse, MyObject.class);
            } else {
                log.error("API call failed with response code: {}", responseCode);
            }
        } catch (IOException e) {
            log.error("Error during API call: {}", e.getMessage());
            // 예외 처리
        }
        return jsonResponse;
    }
}
