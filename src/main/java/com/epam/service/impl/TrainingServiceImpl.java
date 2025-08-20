package com.epam.service.impl;

import com.epam.dao.TrainingDao;
import com.epam.model.Training;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
import com.epam.service.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author jdmon on 18/08/2025
 * @project springbasegymcrm
 */
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingDao trainingDao;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingTypeService trainingTypeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    public TrainingServiceImpl(TrainingDao trainingDao, TraineeService traineeService,
                               TrainerService trainerService, TrainingTypeService trainingTypeService) {
        this.trainingDao = trainingDao;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingTypeService = trainingTypeService;
    }

    @Override
    @Transactional
    public void createTraining(String traineeUsername, String trainerUsername, String trainingName,
                               Long trainingType, LocalDate trainingDate, int durationInMinutes) {

        Training trainingToSave = new Training(traineeService.selectTraineeByUsername(traineeUsername),
                trainerService.selectTrainerByUsername(trainerUsername), trainingName,
                trainingTypeService.selectTrainingTypeById(trainingType),trainingDate, durationInMinutes);
        trainingToSave = trainingDao.save(trainingToSave);
        LOGGER.debug("The {} was saved", trainingToSave);
    }
}
