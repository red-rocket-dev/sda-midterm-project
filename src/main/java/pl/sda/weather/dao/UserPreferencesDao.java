package pl.sda.weather.dao;

import pl.sda.weather.exceptions.NotUniqueLoginFound;
import pl.sda.weather.model.UserEntity;
import pl.sda.weather.model.UserPreferencesEntity;
import pl.sda.weather.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class UserPreferencesDao {
    public void save(UserPreferencesEntity preferencesEntity) {
        EntityManager em = EntityManagerUtil.get();
        em.getTransaction().begin();
        em.persist(preferencesEntity);
        em.getTransaction().commit();
        em.close();
    }

    public UserPreferencesEntity findById(Long id) {
        EntityManager em = EntityManagerUtil.get();
        UserPreferencesEntity preferences = em.find(UserPreferencesEntity.class, id);
        em.close();
        return preferences;
    }

    public void update(UserPreferencesEntity userPreferences) {
        EntityManager em = EntityManagerUtil.get();
        em.getTransaction().begin();
        em.merge(userPreferences);
        em.getTransaction().commit();
        em.close();
    }
}
