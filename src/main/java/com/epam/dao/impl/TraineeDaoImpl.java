package com.epam.dao.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.storage.TraineeStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TraineeDaoImpl implements TraineeDao {

    private final TraineeStorage storage;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);

    public TraineeDaoImpl(TraineeStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Trainee entity) {
        LOGGER.info("Saving trainee with Id: {}", entity.getUserId());
        storage.getTraineeMap().put(entity.getUserId(), entity);
        LOGGER.info("Trainee saved successfully {}", entity.getUserName());
    }

    @Override
    public Trainee findById(long id) {
        LOGGER.info("selecting trainee with Id: {}", id );
        return storage.getTraineeMap().get(id);
    }

    @Override
    public List<Trainee> findAll() {
        LOGGER.info("selecting all active trainees ");
        return storage.getTraineeMap().values()
                .stream().filter(User::isActive).toList();
    }

    @Override
    public void update(Trainee entity) {
        LOGGER.info("Updating trainee with Id: {}", entity.getUserId());
        storage.getTraineeMap().put(entity.getUserId(),entity);
        LOGGER.info("Trainee updated successfully {}", entity.getUserName());
    }

    @Override
    public void deletedById(long id) {
        LOGGER.info("Logical deleting trainee with Id: {}", id);
        Trainee traineeToDeleted= storage.getTraineeMap().get(id);
        traineeToDeleted.setActive(false);
        storage.getTraineeMap().put(traineeToDeleted.getUserId(),traineeToDeleted);
        LOGGER.info("Trainee logical deleting successfully {}", traineeToDeleted.getUserName());
    }
}
