package pl.sda.weather.dao;

import pl.sda.weather.model.UserEntity;
import pl.sda.weather.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDao {
    public void save(UserEntity userEntity) {
        EntityManager em = EntityManagerUtil.get();
        em.getTransaction().begin();
        em.persist(userEntity);
        em.getTransaction().commit();
        em.close();
    }

    public UserEntity findById(Long id) {
        EntityManager em = EntityManagerUtil.get();
        UserEntity userEntity = em.find(UserEntity.class, id);
        em.close();
        return userEntity;
    }

    public List<UserEntity> findAll() {
        EntityManager em = EntityManagerUtil.get();
        List<UserEntity> allUsers = em.createQuery("FROM UserEntity", UserEntity.class).getResultList();
        em.close();
        return allUsers;
    }

    public UserEntity findByLogin(String login) {
        EntityManager em = EntityManagerUtil.get();
        UserEntity byLogin = em.createQuery("FROM UserEntity WHERE login=:login", UserEntity.class)
                .setParameter("login", login)
                .getSingleResult();
        em.close();
        return byLogin;

    }
}
