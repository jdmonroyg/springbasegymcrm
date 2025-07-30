package com.epam.service.impl;

import com.epam.dao.TrainingDao;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
import com.epam.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    public TrainingServiceImpl(TrainingDao trainingDao, TraineeService traineeService,
                               TrainerService trainerService) {
        this.trainingDao = trainingDao;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @Override
    public void createTraining(long traineeId, long trainerId, String trainingName, String type,
                               LocalDate trainingDate, int durationInMinutes) {
        ValidationUtil.requireNonBlank(trainingName, "trainingName");
        ValidationUtil.requireNonBlank(type, "type");
        ValidationUtil.requireNonNull(trainingDate, "trainingDate");
        ValidationUtil.requirePositive(durationInMinutes, "durationInMinutes");
        traineeService.selectTrainee(traineeId);
        trainerService.selectTrainer(trainerId);

        Training trainingToCreate = new Training();
        trainingToCreate.setTrainingId(Training.generateNextId());
        trainingToCreate.setTraineeId(traineeId);
        trainingToCreate.setTrainerId(trainerId);
        trainingToCreate.setTrainingName(trainingName);
        trainingToCreate.setType(TrainingType.valueOf(type.toUpperCase()));
        trainingToCreate.setTrainingDate(trainingDate);
        trainingToCreate.setDurationInMinutes(durationInMinutes);
        trainingDao.save(trainingToCreate);
        LOGGER.debug("The {} was saved", trainingToCreate);
    }

    @Override
    public Training selectTraining(long id) {
        Training trainingSelected = trainingDao.findById(id);
        ValidationUtil.requireExisting(trainingSelected, "Training with id: "+id);
        return trainingSelected;
    }

    @Override
    public List<Training> selectAllTrainings() {
        return trainingDao.findAll();
    }
}
