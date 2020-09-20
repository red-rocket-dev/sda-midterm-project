package pl.sda.weather.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserPreferencesEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String defaultCity;
    private Long defaultAmountOfDaysAhead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultCity() {
        return defaultCity;
    }

    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }

    public Long getDefaultAmountOfDaysAhead() {
        return defaultAmountOfDaysAhead;
    }

    public void setDefaultAmountOfDaysAhead(Long defaultAmountOfDaysAhead) {
        this.defaultAmountOfDaysAhead = defaultAmountOfDaysAhead;
    }
}
