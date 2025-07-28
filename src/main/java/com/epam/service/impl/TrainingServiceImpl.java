package com.epam.service.impl;

import com.epam.dao.imp.TrainingDao;
import com.epam.model.Training;
import com.epam.service.TrainingService;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDao trainingDao;

    public TrainingServiceImpl(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Override
    public void createTraining(Training training) {
        trainingDao.save(training);
    }

    @Override
    public Training selectTraining(long id) {
        return trainingDao.findById(id);
    }
}
