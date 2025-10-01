package com.epam.service.impl;

import com.epam.dto.request.CreateTrainingRequestDto;
import com.epam.exception.InactiveUserException;
import com.epam.exception.NotFoundException;
import com.epam.mapper.TrainingMapper;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.repository.TraineeRepository;
import com.epam.repository.TrainerRepository;
import com.epam.repository.TrainingRepository;
import com.epam.service.AuthService;
import com.epam.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jdmon on 18/08/2025
 * @project springbasegymcrm
 */
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingMapper trainingMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    public TrainingServiceImpl(TrainingRepository trainingRepository, TraineeRepository traineeRepository,
                               TrainerRepository trainerRepository, TrainingMapper trainingMapper ) {
        this.trainingRepository = trainingRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingMapper = trainingMapper;
    }

    @Override
    @Transactional
    public void createTraining(String token, CreateTrainingRequestDto dto) {
        Trainee trainee = traineeRepository.findByUsername(dto.traineeUsername())
                .orElseThrow(() -> new NotFoundException("Trainee was not found"));
        Trainer trainer = trainerRepository.findByUsername(dto.trainerUsername())
                .orElseThrow(() -> new NotFoundException("Trainer was not found"));
        if (!trainee.getActive() || !trainer.getActive()) {
            throw new InactiveUserException("Cannot create training: trainee or trainer is inactive");
        }
        Training trainingToSave = trainingMapper.CreateTrainingRequestToTraining(dto);
        trainingToSave.setTrainee(trainee);
        trainingToSave.setTrainer(trainer);
        trainingToSave.setTrainingType(trainer.getSpecialization());
        trainingRepository.save(trainingToSave);
        trainee.getTrainers().add(trainer);
        traineeRepository.save(trainee);
        LOGGER.debug("The training {} was saved", trainingToSave.getTrainingName());
    }
}
