package pl.sda.weather.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    private static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("midterm");
    }

    public static EntityManager get() {
        return entityManagerFactory.createEntityManager();
    }
}
