package com.epam.service.impl;

//import com.epam.client.WorkloadClient;
import com.epam.dto.request.CreateTrainingRequestDto;
import com.epam.dto.request.WorkloadRequestDTO;
import com.epam.exception.NotFoundException;
import com.epam.mapper.TrainingMapper;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.repository.TraineeRepository;
import com.epam.repository.TrainerRepository;
import com.epam.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jms.core.JmsTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class TrainingServiceImplTest {

    private TrainingRepository trainingRepository;
    private TraineeRepository traineeRepository;
    private TrainerRepository trainerRepository;
    private TrainingMapper trainingMapper;
    private JmsTemplate jmsTemplate;
    private TrainingServiceImpl service;

    @BeforeEach
    void setUp() {
        trainingRepository = mock(TrainingRepository.class);
        traineeRepository = mock(TraineeRepository.class);
        trainerRepository = mock(TrainerRepository.class);
        trainingMapper = mock(TrainingMapper.class);
        jmsTemplate = mock(JmsTemplate.class);

        service = new TrainingServiceImpl(trainingRepository, traineeRepository, trainerRepository, trainingMapper, jmsTemplate);
    }

    @Test
    void createTrainingShouldThrowWhenTraineeNotFound() {
        CreateTrainingRequestDto dto = new CreateTrainingRequestDto("training1", "traineeUser", "trainerUser", LocalDate.now(), 60);
        when(traineeRepository.findByUsername("traineeUser")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.createTraining(dto));
    }

    @Test
    void deleteTrainingShouldRemoveAndNotifyWorkload() {
        Training training = new Training();
        training.setId(1L);
        training.setTrainingDate(LocalDate.now());
        training.setDurationInMinutes(60);

        Trainer trainer = new Trainer();
        trainer.setUsername("trainerUser");
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setActive(true);
        trainer.setSpecialization(new TrainingType());

        training.setTrainer(trainer);

        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));

        service.deleteTraining(1L);

        verify(trainingRepository).delete(training);
        verify(jmsTemplate).convertAndSend(eq("workload.queue"), any(WorkloadRequestDTO.class), any());
    }

    @Test
    void deleteTrainingShouldThrowWhenNotFound() {
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.deleteTraining(1L));
    }
}