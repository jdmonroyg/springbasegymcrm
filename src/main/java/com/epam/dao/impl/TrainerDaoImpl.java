package com.epam.dao.impl;

import com.epam.dao.TrainerDao;
import com.epam.model.Trainer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainerDaoImpl implements TrainerDao {
    @PersistenceContext
    EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @Override
    public Trainer save(Trainer trainer) {
        LOGGER.info("Saving trainer with username: {}", trainer.getUsername());
        return entityManager.merge(trainer);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        LOGGER.info("selecting trainer with username: {}", username );
        String jpql = "Select t from Trainer t where LOWER(t.username) = LOWER(:username)";
        TypedQuery<Trainer> jpqlQuery = entityManager.createQuery(jpql, Trainer.class);
        jpqlQuery.setParameter("username", username);
        try {
            return Optional.ofNullable(jpqlQuery.getSingleResult());
        } catch (NoResultException ex){
            return Optional.empty();
        }
    }

    @Override
    public List<Trainer> findUnassignedTrainersByTraineeUsername(String traineeUsername) {
        LOGGER.info("Find unassigned trainer by traineeUsername {}", traineeUsername );
        String jpql = "Select tr From Trainer tr Where tr.id NOT IN " +
                "(Select t.trainer.id From Training t Where LOWER(t.trainee.username) = LOWER(:username))";
        TypedQuery<Trainer> jpqlQuery = entityManager.createQuery(jpql, Trainer.class);
        jpqlQuery.setParameter("username", traineeUsername);
        return jpqlQuery.getResultList();
    }
}
