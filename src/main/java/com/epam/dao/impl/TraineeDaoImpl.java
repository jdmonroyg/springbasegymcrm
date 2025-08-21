package com.epam.dao.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TraineeDaoImpl implements TraineeDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);


    @Override
    public Trainee save(Trainee trainee) {
        LOGGER.info("Saving trainee with username: {}", trainee.getUsername());
        return entityManager.merge(trainee);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        LOGGER.info("selecting trainee with username: {}", username );
        String jpql = "Select t from Trainee t where LOWER(t.username) = LOWER(:username)";
        TypedQuery<Trainee> jpqlQuery = entityManager.createQuery(jpql, Trainee.class);
        jpqlQuery.setParameter("username", username);
        try {
            return Optional.ofNullable(jpqlQuery.getSingleResult());
        } catch (NoResultException ex){
            return Optional.empty();
        }
    }

    @Override
    public void deletedByUsername(Trainee trainee) {
        LOGGER.info("Hard deleting trainee with username: {}", trainee.getUsername());
        entityManager.remove(trainee);
    }
}
