package com.epam.dao.imp;

import com.epam.dao.BaseDao;
import com.epam.model.Training;
import com.epam.storage.TrainingStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainingDao implements BaseDao<Training> {

    private final TrainingStorage storage;

    public TrainingDao(TrainingStorage storage) {
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

    @Override
    public void update(Training entity) {
        if(storage.getTrainingMap().containsKey(entity.getTrainingId())){
            storage.getTrainingMap().put(entity.getTrainingId(),entity);
        }else storage.getTrainingMap().put(Training.generateNextId(),entity);
    }

    @Override
    public void deletedById(long id) {
        storage.getTrainingMap().remove(id);
    }
}
