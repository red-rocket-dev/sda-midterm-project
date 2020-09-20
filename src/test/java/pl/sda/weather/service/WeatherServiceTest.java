package pl.sda.weather.service;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class WeatherServiceTest {

    @Test
    public void testTest() {
        fail("failed");
    }

    @Test
    public void testTest1() {
        UserService mock = mock(UserService.class);
        when(mock.all()).thenReturn(Collections.emptyList());
        assertThat(mock.all()).isEmpty();
    }

}