package pl.sda.weather.service;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.sda.weather.dto.WindInformation;
import pl.sda.weather.provider.OpenWeatherMapHttpClient;
import pl.sda.weather.provider.model.OpenWeatherResponse;
import pl.sda.weather.provider.model.Wind;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class WeatherServiceTest {
    private static final double WIND_DEG = 1.0;
    private static final double WIND_SPEED = 50.0;

    @Test
    public void whenWeatherAvailableThenReturnProperResponse() throws IOException, InterruptedException {
        //given
        // <tutaj ustawiamy sobie srodowisko testowe>
        OpenWeatherMapHttpClient httpClientMock = mock(OpenWeatherMapHttpClient.class);
        OpenWeatherResponse resp = new OpenWeatherResponse();
        Wind wind = new Wind();
        wind.setSpeed(WIND_SPEED);
        wind.setDeg(WIND_DEG);
        resp.setWind(wind);

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

}