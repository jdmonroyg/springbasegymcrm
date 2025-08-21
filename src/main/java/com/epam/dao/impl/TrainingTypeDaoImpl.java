package com.epam.dao.impl;

import com.epam.dao.TrainingTypeDao;
import com.epam.model.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {
    @PersistenceContext
    EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeDaoImpl.class);

    @Override
    public Optional<TrainingType> findById(Long id) {
        LOGGER.info("selecting trainingType with id: {}", id );
        return Optional.ofNullable(entityManager.find(TrainingType.class, id));
    }
}
