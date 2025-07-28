package com.epam.service.impl;

import com.epam.dao.imp.TraineeDao;
import com.epam.model.Trainee;
import com.epam.service.TraineeService;
import org.springframework.stereotype.Service;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDao traineeDao;

    public TraineeServiceImpl(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Override
    public void createTrainee(Trainee trainee) {
        traineeDao.save(trainee);
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        traineeDao.update(trainee);
    }

    @Override
    public void deleteTrainee(long id) {
        traineeDao.deletedById(id);
    }

    @Override
    public Trainee selectTrainee(long id) {
        return traineeDao.findById(id);
    }
}
