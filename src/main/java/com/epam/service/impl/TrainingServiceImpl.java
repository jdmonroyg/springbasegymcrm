package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.dao.TrainerDao;
import com.epam.dao.TrainingDao;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
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
    private final TraineeDao traineeDao;
    private final TrainerDao trainerDao;
    private final TrainingTypeService trainingTypeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    public TrainingServiceImpl(TrainingDao trainingDao, TraineeDao traineeDao, TrainerDao trainerDao, TrainingTypeService trainingTypeService) {
        this.trainingDao = trainingDao;
        this.traineeDao = traineeDao;
        this.trainerDao = trainerDao;
        this.trainingTypeService = trainingTypeService;
    }

    @Override
    @Transactional
    public void createTraining(String traineeUsername, String trainerUsername, String trainingName,
                               Long trainingType, LocalDate trainingDate, int durationInMinutes) {
        Trainee trainee = traineeDao.findByUsername(traineeUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainee was not found"));
        Trainer trainer = trainerDao.findByUsername(trainerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainer was not found"));;
        Training trainingToSave = new Training(trainee, trainer, trainingName,
                trainingTypeService.selectTrainingTypeById(trainingType),trainingDate, durationInMinutes);
        trainingToSave = trainingDao.save(trainingToSave);
        trainee.getTrainers().add(trainer);
        traineeDao.save(trainee);
        LOGGER.debug("The {} was saved", trainingToSave);
    }
}
