package com.epam.service.impl;

import com.epam.dto.request.*;
import com.epam.dto.response.*;
import com.epam.exception.EmailAlreadyExistsException;
import com.epam.mapper.TrainerMapper;
import com.epam.mapper.TrainingMapper;
import com.epam.model.Role;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.repository.TrainerRepository;
import com.epam.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.epam.service.TrainerService;
import com.epam.service.TrainingTypeService;
import com.epam.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainingTypeService trainingTypeService;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final PasswordEncoder encoder;
    private final TrainerMapper trainerMapper;
    private final TrainingMapper trainingMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    public TrainerServiceImpl(TrainerRepository trainerRepository, TrainingTypeService trainingTypeService,
                              UserRepository userRepository, UserUtil userUtil, PasswordEncoder encoder,
                              TrainerMapper trainerMapper,  TrainingMapper trainingMapper) {
        this.trainerRepository = trainerRepository;
        this.trainingTypeService = trainingTypeService;
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.encoder = encoder;
        this.trainerMapper = trainerMapper;
        this.trainingMapper = trainingMapper;
    }

    @Override
    @Transactional
    public CreateUserResponseDto createTrainer(CreateTrainerRequestDto dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already associated with an account");
        }
        String username = userUtil.generateUsername(dto.firstName(), dto.lastName(),
                userRepository.findAllUsernames());
        String firstPassword = userUtil.generateRandomPassword();
        TrainingType trainingType = trainingTypeService.selectTrainingTypeById(dto.specializationId());
        Trainer trainer = trainerMapper.createTrainerRequestDtoToTrainer(dto);
        trainer.setUsername(username);
        trainer.setPassword(encoder.encode(firstPassword));
        trainer.setActive(true);
        trainer.setRole(Role.TRAINER);
        trainerRepository.save(trainer);
        LOGGER.debug("The trainer {} was saved", username);
        return new CreateUserResponseDto(username, firstPassword);

    }

    @Override
    @Transactional(readOnly = true)
    public TrainerResponseDto selectTrainerByUsername(String username) {
        Trainer trainerSelected = getTrainerByUsername(username);
        LOGGER.debug("The trainer {} was select", trainerSelected.getUsername());
        return trainerMapper.trainerToTrainerResponseDto(trainerSelected);
    }

    @Override
    @Transactional
    public TrainerUpdatedResponseDto updateTrainer(String username, UpdateTrainerRequestDto dto) {
        Trainer trainerToUpdated = getTrainerByUsername(username);
        trainerToUpdated.setFirstName(dto.firstName());
        trainerToUpdated.setLastName(dto.lastName());
        trainerToUpdated.setSpecialization(trainingTypeService
                .selectTrainingTypeById(dto.specializationId()));
        trainerToUpdated.setActive(dto.active());
        trainerRepository.save(trainerToUpdated);
        LOGGER.debug("The trainer {} was updated", trainerToUpdated.getUsername());
        return trainerMapper.trainerToTrainerUpdResponseDto(trainerToUpdated);
    }

    @Override
    @Transactional
    public void changeActiveStatus(String username, PatchUserRequestDto dto) {
        Trainer trainerToSwitchStatus = getTrainerByUsername(username);
        trainerToSwitchStatus.setActive(dto.active());
        trainerRepository.save(trainerToSwitchStatus);
        LOGGER.debug("Trainer '{}' active status changed to {}",
                trainerToSwitchStatus.getUsername(),
                trainerToSwitchStatus.getActive());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainerTrainingsResponseDto> getTrainerTrainings(String username,
                                                                 TrainerTrainingsFilterRequestDto dto) {
        Trainer trainer = getTrainerByUsername(username);
        List <Training> filterTrainings = trainer.getTrainings().stream()
                .filter(t -> dto.periodFrom() == null || !t.getTrainingDate().isBefore(dto.periodFrom()))
                .filter(t -> dto.periodTo()== null ||  !t.getTrainingDate().isAfter((dto.periodTo())))
                .filter(t -> dto.traineeName() == null ||
                        t.getTrainee().getFirstName().equalsIgnoreCase(dto.traineeName()))
                .toList();
        LOGGER.info("The list of the trainer's training sessions was received with size {}",
                filterTrainings.size());
        filterTrainings.forEach(t ->
                LOGGER.debug("Training -> trainingName: {}, date: {}, duration: {} min," +
                                " trainee: {}, trainer: {}, trainerType: {}, trainingType: {}",
                        t.getTrainingName(), t.getTrainingDate(), t.getDurationInMinutes(),
                        t.getTrainee().getUsername(),t.getTrainer().getUsername(),
                        t.getTrainer().getSpecialization().getName(), t.getTrainingType().getName())
        );
        return trainingMapper.trainingsToTrainerTrainingsResponseDto(filterTrainings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainersResponseDto> getUnassignedTrainersByTraineeUsername(String traineeUsername) {
        List<Trainer> trainers = trainerRepository.findActiveUnassignedTrainersByTraineeUsername(traineeUsername);
        LOGGER.info("The list of the trainers was received with size {}",
                trainers.size());
        trainers.forEach( t ->
                LOGGER.debug("Trainer-> username: {}, type: {}", t.getUsername(),
                        t.getSpecialization().getName())
        );
        return trainerMapper.trainersToTrainersResponseDto(trainers);
    }

    @Transactional(readOnly = true)
    private Trainer getTrainerByUsername(String username) {
        return trainerRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Trainer was not found"));
    }

}
