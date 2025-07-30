package com.epam.dao.impl;

import com.epam.dao.TrainingDao;
import com.epam.model.Training;
import com.epam.storage.TrainingStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainingDaoImpl implements TrainingDao {

    private final TrainingStorage storage;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDaoImpl.class);

    public TrainingDaoImpl(TrainingStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Training entity) {
        LOGGER.info("Saving trainer with Id: {}", entity.getTrainingId());
        storage.getTrainingMap().put(entity.getTrainingId(),entity);
        LOGGER.info("Trainer saved successfully {}", entity.getTrainingName());
    }

    @Override
    public Training findById(long id) {
        LOGGER.info("selecting training with Id: {}", id );
        return storage.getTrainingMap().get(id);
    }

    @Override
    public List<Training> findAll() {
        LOGGER.info("selecting all trainings ");
        return storage.getTrainingMap().values()
                .stream().toList();
    }

}
