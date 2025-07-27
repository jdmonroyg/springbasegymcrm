package com.epam.dao;

import com.epam.model.Trainer;
import com.epam.storage.TrainerStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Repository
public class TrainerDao implements BaseDao<Trainer>{

    private static final String NAMESPACE = "trainer";

    private TrainerStorage storage;

    public TrainerDao(TrainerStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Trainer entity) {

    }

    @Override
    public Trainer findById(long id) {
        return null;
    }

    @Override
    public List<Trainer> findAll() {
        return List.of();
    }

    @Override
    public void update(Trainer entity) {

    }

    @Override
    public void deletedById(long id) {

    }
}
