package com.epam.dao.imp;

import com.epam.dao.BaseDao;
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
public class TraineeDao implements BaseDao<Trainee> {

    private final TraineeStorage storage;

    public TraineeDao(TraineeStorage storage) {
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
        if(storage.getTraineeMap().containsKey(entity.getUserId())){
            storage.getTraineeMap().put(entity.getUserId(),entity);
        }else storage.getTraineeMap().put(User.generateNextId(),entity);
    }

    @Override
    public void deletedById(long id) {
        Trainee traineeToDeleted= storage.getTraineeMap().get(id);
        traineeToDeleted.setActive(false);
        update(traineeToDeleted);
    }
}
