package com.epam.service.impl;

import com.epam.client.WorkloadClient;
import com.epam.dto.request.CreateTrainingRequestDto;
import com.epam.dto.request.WorkloadRequestDTO;
import com.epam.exception.DownstreamServiceException;
import com.epam.exception.InactiveUserException;
import com.epam.exception.NotFoundException;
import com.epam.mapper.TrainingMapper;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.repository.TraineeRepository;
import com.epam.repository.TrainerRepository;
import com.epam.repository.TrainingRepository;
import com.epam.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
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
    private final WorkloadClient workloadClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    public TrainingServiceImpl(TrainingRepository trainingRepository, TraineeRepository traineeRepository,
                               TrainerRepository trainerRepository, TrainingMapper trainingMapper, WorkloadClient workloadClient) {
        this.trainingRepository = trainingRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingMapper = trainingMapper;
        this.workloadClient = workloadClient;
    }

    @Override
    @Transactional
    public void createTraining(CreateTrainingRequestDto dto) {
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

        WorkloadRequestDTO workloadRequest = new WorkloadRequestDTO(trainer.getUsername(), trainer.getFirstName(),
                trainer.getLastName(), trainer.getActive(), trainingToSave.getTrainingDate(),
                trainingToSave.getDurationInMinutes(), "ADD");

        try {
            LOGGER.debug("Notifying workload service for trainer {}", trainer.getUsername());
            workloadClient.updateWorkload(workloadRequest, MDC.get("transactionId"));
        } catch (Exception e){
            throw new DownstreamServiceException("Failed to update workload service: " + e.getMessage());
        }


    }

    @Override
    @Transactional
    public void deleteTraining(Long id) {
        LOGGER.info("Deleting training with id {}", id);
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Training was not found"));
        trainingRepository.delete(training);

        WorkloadRequestDTO workloadRequest = new WorkloadRequestDTO(training.getTrainer().getUsername(),
                training.getTrainer().getFirstName(),
                training.getTrainer().getLastName(), training.getTrainer().getActive(),
                training.getTrainingDate(),
                training.getDurationInMinutes(), "DELETE");

        try {
            LOGGER.debug("Deleted training {}, notifying workload service", training.getId());
            workloadClient.updateWorkload(workloadRequest, MDC.get("transactionId"));
        } catch (Exception e){
            throw new DownstreamServiceException("Failed to delete workload service: " + e.getMessage());
        }
    }
}
