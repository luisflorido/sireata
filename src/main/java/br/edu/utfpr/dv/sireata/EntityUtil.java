package br.edu.utfpr.dv.sireata;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityUtil {

    @Getter
    protected EntityManager entityManager;

    public EntityUtil() {
        String FACTORY_NAME = "sireata";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(FACTORY_NAME);
        entityManager = factory.createEntityManager();
    }
}
