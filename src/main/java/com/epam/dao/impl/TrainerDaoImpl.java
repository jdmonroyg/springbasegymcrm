package com.epam.dao.impl;

import com.epam.dao.TrainerDao;
import com.epam.model.Trainer;
import com.epam.model.User;
import com.epam.storage.TrainerStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainerDaoImpl implements TrainerDao {

    private final TrainerStorage storage;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);

    public TrainerDaoImpl(TrainerStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Trainer entity) {
        LOGGER.info("Saving trainer with Id: {}", entity.getUserId());
        storage.getTrainerMap().put(entity.getUserId(),entity);
        LOGGER.info("Trainer saved successfully {}", entity.getUserName());
    }

    @Override
    public Trainer findById(long id) {
        LOGGER.info("selecting trainer with Id: {}", id );
        return storage.getTrainerMap().get(id);
    }

    @Override
    public List<Trainer> findAll() {
        LOGGER.info("selecting all active trainers ");
        return storage.getTrainerMap().values()
                .stream().filter(User::isActive).toList();
    }

    @Override
    public void update(Trainer entity) {
        LOGGER.info("Updating trainer with Id: {}", entity.getUserId());
        storage.getTrainerMap().put(entity.getUserId(),entity);
        LOGGER.info("Trainer updated successfully {}", entity.getUserName());
    }

}
