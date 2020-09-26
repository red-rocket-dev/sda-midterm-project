package pl.sda.weather.service;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.sda.weather.dto.WeatherInformation;
import pl.sda.weather.dto.WindInformation;
import pl.sda.weather.provider.OpenWeatherMapHttpClient;
import pl.sda.weather.provider.model.OpenWeatherResponse;
import pl.sda.weather.provider.model.WeatherInfo;
import pl.sda.weather.provider.model.Wind;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class WeatherServiceTest {
    private static final double WIND_DEG = 1.0;
    private static final double WIND_SPEED = 50.0;
    private static final int PRESSURE = 15;
    private static final int HUMIDITY = 22;
    private static final int TEMPERATURE = 11;


    //givenUserExistsWhenLoginThenAuthorize




    @Test
    public void givenWeatherAvailableWhenGetWindThenReturnProperResponse() throws IOException, InterruptedException {
        //given
        // <tutaj ustawiamy sobie dane testowe>
        OpenWeatherMapHttpClient httpClientMock = mock(OpenWeatherMapHttpClient.class);
        OpenWeatherResponse resp = createWindResponse();
        when(httpClientMock.getWeather(any())).thenReturn(resp);
        WeatherService weatherService = new WeatherService(httpClientMock);
        //when
        // <tutaj wywolujemy funkcje, ktora chcemy testowac>
        WindInformation windInformationForKatowice = weatherService.getWind("Katowice");
        //then
        assertThat(windInformationForKatowice.getForce()).isEqualTo(WIND_SPEED);
        assertThat(windInformationForKatowice.getDegrees()).isEqualTo(WIND_DEG);
        // <asercje (sprawdzamy, czy mamy oczekiwany rezultat testu)>
    }

    @Test
    public void givenWeatherAvailableWhenGetTemperatureThenReturnProperResponse() throws IOException, InterruptedException {
        //given
        // <tutaj ustawiamy sobie srodowisko testowe>
        OpenWeatherMapHttpClient httpClientMock = mock(OpenWeatherMapHttpClient.class);
        when(httpClientMock.getWeather(any())).thenReturn(createTemperatureResponse());
        WeatherService weatherService = new WeatherService(httpClientMock);
        //when
        // <tutaj wywolujemy funkcje, ktora chcemy testowac>
        double temperature = weatherService.getTemperature("Katowice");
        //then
        assertThat(temperature).isEqualTo(TEMPERATURE);
        // <asercje (sprawdzamy, czy mamy oczekiwany rezultat testu)>
    }

    @Test
    public void givenWeatherAvailableWhenGetPressureThenReturnProperResponse() throws IOException, InterruptedException {
        //given
        // <tutaj ustawiamy sobie srodowisko testowe>
        OpenWeatherMapHttpClient httpClientMock = mock(OpenWeatherMapHttpClient.class);
        when(httpClientMock.getWeather(any())).thenReturn(createPressureResponse());
        WeatherService weatherService = new WeatherService(httpClientMock);
        //when
        // <tutaj wywolujemy funkcje, ktora chcemy testowac>
        double pressure = weatherService.getPressure("Katowice");
        //then
        assertThat(pressure).isEqualTo(PRESSURE);
        // <asercje (sprawdzamy, czy mamy oczekiwany rezultat testu)>
    }

    @Test
    public void givenWeatherAvailableWhenGetHumidityThenReturnProperResponse() throws IOException, InterruptedException {
        //given
        // <tutaj ustawiamy sobie srodowisko testowe>
        OpenWeatherMapHttpClient httpClientMock = mock(OpenWeatherMapHttpClient.class);
        when(httpClientMock.getWeather(any())).thenReturn(createHumidityResponse());
        WeatherService weatherService = new WeatherService(httpClientMock);
        //when
        // <tutaj wywolujemy funkcje, ktora chcemy testowac>
        double humidity = weatherService.getHumidity("Katowice");
        //then
        assertThat(humidity).isEqualTo(HUMIDITY);
        // <asercje (sprawdzamy, czy mamy oczekiwany rezultat testu)>
    }

    @Test
    public void givenWeatherAvailableWhenGetWeatherThenReturnProperResponse() throws IOException, InterruptedException {
        //given
        // <tutaj ustawiamy sobie srodowisko testowe>
        OpenWeatherMapHttpClient httpClientMock = mock(OpenWeatherMapHttpClient.class);
        when(httpClientMock.getWeather(any())).thenReturn(createFullWeatherResponse());
        WeatherService weatherService = new WeatherService(httpClientMock);
        //when
        // <tutaj wywolujemy funkcje, ktora chcemy testowac>
        WeatherInformation weatherInformation = weatherService.getWeather("Katowice");
        //then
        assertThat(weatherInformation.getHumidity()).isEqualTo(HUMIDITY);
        assertThat(weatherInformation.getTemperature()).isEqualTo(TEMPERATURE);
        assertThat(weatherInformation.getPressure()).isEqualTo(PRESSURE);
        WindInformation windInformation = weatherInformation.getWindInformation();
        assertThat(windInformation.getForce()).isEqualTo(WIND_SPEED);
        assertThat(windInformation.getDegrees()).isEqualTo(WIND_DEG);

        // <asercje (sprawdzamy, czy mamy oczekiwany rezultat testu)>
    }


    private OpenWeatherResponse createFullWeatherResponse() {
        OpenWeatherResponse resp = new OpenWeatherResponse();
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setHumidity(HUMIDITY);
        weatherInfo.setTemp(TEMPERATURE);
        weatherInfo.setPressure(PRESSURE);
        Wind wind = new Wind();
        wind.setSpeed(WIND_SPEED);
        wind.setDeg(WIND_DEG);
        resp.setWind(wind);
        resp.setMain(weatherInfo);
        return resp;
    }

    private OpenWeatherResponse createWindResponse() {
        OpenWeatherResponse resp = new OpenWeatherResponse();
        Wind wind = new Wind();
        wind.setSpeed(WIND_SPEED);
        wind.setDeg(WIND_DEG);
        resp.setWind(wind);
        return resp;
    }

    private OpenWeatherResponse createPressureResponse() {
        OpenWeatherResponse resp = new OpenWeatherResponse();
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setPressure(PRESSURE);
        resp.setMain(weatherInfo);
        return resp;
    }

    private OpenWeatherResponse createHumidityResponse() {
        OpenWeatherResponse resp = new OpenWeatherResponse();
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setHumidity(HUMIDITY);
        resp.setMain(weatherInfo);
        return resp;
    }


    private OpenWeatherResponse createTemperatureResponse() {
        OpenWeatherResponse resp = new OpenWeatherResponse();
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setTemp(TEMPERATURE);
        resp.setMain(weatherInfo);
        return resp;
    }
}