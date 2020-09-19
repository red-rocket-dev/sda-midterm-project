package pl.sda.weather.dto;

public class WeatherInformation {
    private double temperature;
    private double humidity;
    private double pressure;
    private WindInformation windInformation;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public WindInformation getWindInformation() {
        return windInformation;
    }

    public void setWindInformation(WindInformation windInformation) {
        this.windInformation = windInformation;
    }
}
