package com.epam.dao;

import com.epam.model.Trainee;
import com.epam.storage.TraineeStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TraineeDao implements BaseDao<Trainee>{

    private static final String NAMESPACE = "trainee";

    private final TraineeStorage storage;

    public TraineeDao(TraineeStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Trainee entity) {


    }

    @Override
    public Trainee findById(long id) {
        return null;
    }

    @Override
    public List<Trainee> findAll() {
        return List.of();
    }

    @Override
    public void update(Trainee entity) {

    }

    @Override
    public void deletedById(long id) {

    }
}
