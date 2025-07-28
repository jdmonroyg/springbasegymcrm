package com.epam.service.impl;

import com.epam.dao.imp.TrainerDao;
import com.epam.model.Trainer;
import com.epam.service.TrainerService;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDao trainerDao;

    public TrainerServiceImpl(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Override
    public void createTrainer(Trainer trainer) {
        trainerDao.save(trainer);
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        trainerDao.save(trainer);
    }

    @Override
    public Trainer selectTrainer(long id) {
        return trainerDao.findById(id);
    }
}
