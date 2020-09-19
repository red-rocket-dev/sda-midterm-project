package pl.sda.weather.dao;

import pl.sda.weather.model.UserEntity;
import pl.sda.weather.util.EntityManagerUtil;

import javax.persistence.EntityManager;

public class UserDao {
    public void save(UserEntity userEntity) {
        final EntityManager entityManager = EntityManagerUtil.get();
        entityManager.getTransaction().begin();
        entityManager.persist(userEntity);
        entityManager.getTransaction().commit();
    }
}
