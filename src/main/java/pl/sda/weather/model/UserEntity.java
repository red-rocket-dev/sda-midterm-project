package pl.sda.weather.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String login;
    private String password;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private UserPreferencesEntity userPreferences;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserPreferencesEntity getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferencesEntity userPreferences) {
        this.userPreferences = userPreferences;
    }
}
