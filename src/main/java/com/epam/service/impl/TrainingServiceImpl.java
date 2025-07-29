package com.epam.service.impl;

import com.epam.dao.TrainingDao;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.service.TrainingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDao trainingDao;

    public TrainingServiceImpl(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Override
    public void createTraining(long traineeId, long trainerId, String trainingName, String type, LocalDate trainingDate, int trainingDuration) {
        Training trainingToCreate = new Training();
        trainingToCreate.setTraineeId(traineeId);
        trainingToCreate.setTrainerId(trainerId);
        trainingToCreate.setTrainingName(trainingName);
        trainingToCreate.setType(TrainingType.valueOf(type));
        trainingToCreate.setTrainingDate(trainingDate);
        trainingToCreate.setDurationInMinutes(trainingDuration);
        trainingDao.save(trainingToCreate);
    }

    @Override
    public Training selectTraining(long id) {
        return trainingDao.findById(id);
    }

    @Override
    public List<Training> selectAllTrainings() {
        return trainingDao.findAll();
    }
}
