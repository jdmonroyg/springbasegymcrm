package com.epam.dao.imp;

import com.epam.dao.BaseDao;
import com.epam.model.Trainer;
import com.epam.model.User;
import com.epam.storage.TrainerStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainerDao implements BaseDao<Trainer> {

    private final TrainerStorage storage;

    public TrainerDao(TrainerStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Trainer entity) {
        storage.getTrainerMap().put(User.generateNextId(),entity);
    }

    @Override
    public Trainer findById(long id) {
        return storage.getTrainerMap().get(id);
    }

    @Override
    public List<Trainer> findAll() {
        return storage.getTrainerMap().values()
                .stream().filter(User::isActive).toList();
    }

    @Override
    public void update(Trainer entity) {
        if(storage.getTrainerMap().containsKey(entity.getUserId())){
            storage.getTrainerMap().put(entity.getUserId(),entity);
        }else storage.getTrainerMap().put(User.generateNextId(),entity);
    }

    @Override
    public void deletedById(long id) {
        Trainer trainerToDeleted= storage.getTrainerMap().get(id);
        trainerToDeleted.setActive(false);
        update(trainerToDeleted);
    }
}
