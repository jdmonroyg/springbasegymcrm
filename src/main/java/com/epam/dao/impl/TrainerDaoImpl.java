package com.epam.dao.impl;

import com.epam.dao.TrainerDao;
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
public class TrainerDaoImpl implements TrainerDao {

    private final TrainerStorage storage;

    public TrainerDaoImpl(TrainerStorage storage) {
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
        storage.getTrainerMap().put(entity.getUserId(),entity);
    }

}
