package com.epam.dao.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.storage.TraineeStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TraineeDaoImpl implements TraineeDao {

    private final TraineeStorage storage;

    public TraineeDaoImpl(TraineeStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Trainee entity) {
        storage.getTraineeMap().put(User.generateNextId(),entity);
    }

    @Override
    public Trainee findById(long id) {
        return storage.getTraineeMap().get(id);
    }

    @Override
    public List<Trainee> findAll() {
        return storage.getTraineeMap().values()
                .stream().filter(User::isActive).toList();
    }

    @Override
    public void update(Trainee entity) {
        storage.getTraineeMap().put(entity.getUserId(),entity);
    }

    @Override
    public void deletedById(long id) {
        Trainee traineeToDeleted= storage.getTraineeMap().get(id);
        traineeToDeleted.setActive(false);
        update(traineeToDeleted);
    }
}
