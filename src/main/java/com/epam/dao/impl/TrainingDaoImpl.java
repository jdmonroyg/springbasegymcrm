package com.epam.dao.impl;

import com.epam.dao.TrainingDao;
import com.epam.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainingDaoImpl implements TrainingDao {

    @PersistenceContext
    EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @Override
    public Training save(Training training) {
        LOGGER.info("Saving training with name: {}", training.getTrainingName());
        return entityManager.merge(training);

    }
}
