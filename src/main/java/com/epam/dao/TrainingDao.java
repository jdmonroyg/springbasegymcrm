package com.epam.dao;

import com.epam.model.Training;
import com.epam.storage.TrainingStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainingDao implements BaseDao<Training>{

    private static final String NAMESPACE = "training";

    private TrainingStorage storage;

    public TrainingDao(TrainingStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Training entity) {

    }

    @Override
    public Training findById(long id) {
        return null;
    }

    @Override
    public List<Training> findAll() {
        return List.of();
    }

    @Override
    public void update(Training entity) {

    }

    @Override
    public void deletedById(long id) {

    }
}
