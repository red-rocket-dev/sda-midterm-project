package pl.sda.weather.service;

import pl.sda.weather.dto.WeatherInformation;
import pl.sda.weather.dto.WindInformation;
import pl.sda.weather.provider.OpenWeatherMapHttpClient;
import pl.sda.weather.provider.model.OpenWeatherResponse;

import java.io.IOException;

public class WeatherService {

    private OpenWeatherMapHttpClient httpClient;

    public WeatherService(OpenWeatherMapHttpClient openWeatherMapHttpClient) {
        this.httpClient = openWeatherMapHttpClient;
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

    public double getTemperature(String city) throws IOException, InterruptedException {
        OpenWeatherResponse weather = httpClient.getWeather(city);
        return weather.getMain().getTemp();
    }

    public double getHumidity(String city) throws IOException, InterruptedException {
        OpenWeatherResponse weather = httpClient.getWeather(city);
        return weather.getMain().getHumidity();
    }

    public double getPressure(String city) throws IOException, InterruptedException {
        OpenWeatherResponse weather = httpClient.getWeather(city);
        return weather.getMain().getPressure();
    }

    public WeatherInformation getWeather(String city) throws IOException, InterruptedException {
        OpenWeatherResponse weather = httpClient.getWeather(city);
        double temperature = weather.getMain().getTemp();
        int humidity = weather.getMain().getHumidity();
        int pressure = weather.getMain().getPressure();
        double degrees = weather.getWind().getDeg();
        double speed = weather.getWind().getSpeed();
        WindInformation windInformation = new WindInformation();
        windInformation.setForce(speed);
        windInformation.setDegrees(degrees);
        WeatherInformation weatherInformation = new WeatherInformation();
        weatherInformation.setHumidity(humidity);
        weatherInformation.setPressure(pressure);
        weatherInformation.setTemperature(temperature);
        weatherInformation.setWindInformation(windInformation);
        return weatherInformation;
    }
}
