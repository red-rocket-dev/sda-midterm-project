package pl.sda.weather.service;

import pl.sda.weather.dto.WeatherInformation;
import pl.sda.weather.dto.WindInformation;
import pl.sda.weather.provider.OpenWeatherMapHttpClient;
import pl.sda.weather.provider.model.OpenWeatherResponse;

import java.io.IOException;

public class WeatherService {

    private OpenWeatherMapHttpClient httpClient;

    public WeatherService() {
        httpClient = new OpenWeatherMapHttpClient();
    }

    public WindInformation getWind(String city) throws IOException, InterruptedException {
        OpenWeatherResponse weather = httpClient.getWeather(city);
        double deg = weather.getWind().getDeg();
        double speed = weather.getWind().getSpeed();

        WindInformation windInformation = new WindInformation();
        windInformation.setDegrees(deg);
        windInformation.setForce(speed);

        return windInformation;
    }

    public double getTemperature(String city) {
        return 0;
    }

    public double getHumidity(String city) {
        return 0;
    }

    public double getPressure(String city) throws IOException, InterruptedException {
        OpenWeatherResponse weather = httpClient.getWeather(city);
        return weather.getMain().getPressure();
    }

    public WeatherInformation getWeather(String city) {
        return null;
    }
}
