package pl.sda.weather.service;

import pl.sda.weather.dao.UserDao;
import pl.sda.weather.model.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserService {
    private UserDao userDao;

    private UserEntity loggedInUser = null;

    public UserService() {
        this.userDao = new UserDao();
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public Optional<UserEntity> currentUser() {
        return Optional.ofNullable(this.loggedInUser);
    }

    public boolean login(String login, String password) {
        List<List<String>> listOfLists = Arrays.asList(Arrays.asList("a", "b", "c"), Arrays.asList("d", "e", "f"));
        //TODO: lambdy
        return userDao.findByLogin(login)
                .filter(user -> !user.getPassword().equals(password))
                .isPresent();
    }

    public void register(String login, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(login);
        userEntity.setPassword(password);
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
