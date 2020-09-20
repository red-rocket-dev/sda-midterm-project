package pl.sda.weather.service;

import pl.sda.weather.dao.UserDao;
import pl.sda.weather.dao.UserPreferencesDao;
import pl.sda.weather.model.UserEntity;
import pl.sda.weather.model.UserPreferencesEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserService {
    private UserDao userDao;
    private UserPreferencesDao userPreferencesDao;

    private UserEntity loggedInUser = null;

    public UserService() {
        this.userDao = new UserDao();
        this.userPreferencesDao = new UserPreferencesDao();
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public Optional<UserEntity> currentUser() {
        return Optional.ofNullable(this.loggedInUser);
    }

    public boolean login(String login, String password) {
        Optional<UserEntity> maybeLoggedInUser = userDao.findByLogin(login)
                .filter(user -> user.getPassword().equals(password));
        if (maybeLoggedInUser.isPresent()) {
            this.loggedInUser = maybeLoggedInUser.get();
            return true;
        } else {
            return false;
        }
    }


    public void changeDefaultCityOfCurrentUser(String city) {
        UserEntity currentUser = currentUser().get();
        UserPreferencesEntity currentUserPreferences = currentUser.getUserPreferences();
        currentUserPreferences.setDefaultCity(city);
        userPreferencesDao.update(currentUserPreferences);

/*        UserEntity userEntity = currentUser().get();
        userEntity.getUserPreferences().setDefaultCity(city);*/
        /*currentUser()
                .ifPresent(user -> user.getUserPreferences().setDefaultCity(city));*/
    }

    public void register(String login, String password) {
        UserEntity userEntity = new UserEntity();
        UserPreferencesEntity userPreferencesEntity = new UserPreferencesEntity();
        userPreferencesEntity.setDefaultCity("Katowice");
        userPreferencesEntity.setDefaultAmountOfDaysAhead(0L);
        userEntity.setLogin(login);
        userEntity.setPassword(password);
        userEntity.setUserPreferences(userPreferencesEntity);
        //this.userPreferencesDao.save(userPreferencesEntity);
        this.userDao.save(userEntity);
    }

    public UserEntity findById(Long id) {
        return this.userDao.findById(id);
    }

    public Optional<UserEntity> findByLogin(String login) {
        return this.userDao.findByLogin(login);
    }

    public List<UserEntity> all() {
        return this.userDao.findAll();
    }
}
