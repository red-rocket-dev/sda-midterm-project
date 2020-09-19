package pl.sda.weather.provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.sda.weather.provider.model.OpenWeatherResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenWeatherMapHttpClient {
    //TODO: jak bezpiecznie przechowywać delikatne dane (np. hasło czy apikey)
    private final HttpClient httpClient;

    public OpenWeatherMapHttpClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public OpenWeatherResponse getWeather(String city) throws IOException, InterruptedException {
        HttpRequest currentWeatherRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=8af1f753ac7c4d67cd7987b1c374e618"))
                .build();
        String body = httpClient.send(currentWeatherRequest, HttpResponse.BodyHandlers.ofString())
                .body();//do tego miejsca wszystko tak samo jak wyżej
        ObjectMapper objectMapper = new ObjectMapper() //tworzymy nowy obiekt mapper, coś co będzie nam konwertowało łańcuch znaków (JSON) na nasz obiekt OpenWeatherResponse, który wcześniej stworzyliśmy
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// tym możemy wyłączyć rzucanie wyjątków w przypadku kiedy ObjectMapper natrafi w łańcuchu na pole, które nie ma odzwierciedlenia w klasie
        return objectMapper.readValue(body, OpenWeatherResponse.class); // w końcu konwertujemy stringa na obiekt typu podanego w 2 argumencie (bez tego ObjectMapper nie wie na co zamienić tego Stringa)
    }
}
