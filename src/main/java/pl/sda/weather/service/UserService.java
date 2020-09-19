package pl.sda.weather.service;

import pl.sda.weather.dao.UserDao;
import pl.sda.weather.model.UserEntity;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public UserEntity findById(Long id) {
        return this.userDao.findById(id);
    }

    public UserEntity findByLogin(String login) {
        return this.userDao.findByLogin(login);
    }

    public List<UserEntity> all() {
        return this.userDao.findAll();
    }
}
