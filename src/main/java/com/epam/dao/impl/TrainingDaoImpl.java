package com.epam.dao.impl;

import com.epam.dao.TrainingDao;
import com.epam.model.Training;
import com.epam.storage.TrainingStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainingDaoImpl implements TrainingDao {

    private final TrainingStorage storage;

    public TrainingDaoImpl(TrainingStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Training entity) {
        storage.getTrainingMap().put(Training.generateNextId(),entity);
    }

    @Override
    public Training findById(long id) {
        return storage.getTrainingMap().get(id);
    }

    @Override
    public List<Training> findAll() {
        return storage.getTrainingMap().values()
                .stream().toList();
    }

}
