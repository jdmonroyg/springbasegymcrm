package com.epam.service.impl;

import com.epam.dto.request.CreateTraineeRequestDto;
import com.epam.dto.request.PatchUserRequestDto;
import com.epam.dto.request.TraineeTrainingsFilterRequestDto;
import com.epam.dto.request.UpdateTraineeRequestDto;
import com.epam.dto.response.CreateUserResponseDto;
import com.epam.dto.response.TraineeResponseDto;
import com.epam.dto.response.TraineeUpdatedResponseDto;
import com.epam.dto.response.TraineeTrainingsResponseDto;
import com.epam.exception.EmailAlreadyExistsException;
import com.epam.exception.NotFoundException;
import com.epam.mapper.TraineeMapper;
import com.epam.mapper.TrainingMapper;
import com.epam.model.Role;
import com.epam.model.Trainee;
import com.epam.model.Training;
import com.epam.repository.TraineeRepository;
import com.epam.repository.UserRepository;
import com.epam.service.TraineeService;
import com.epam.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jdmon on 15/08/2025
 * @project springbasegymcrm
 */
@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final PasswordEncoder encoder;
    private final TraineeMapper traineeMapper;
    private final TrainingMapper trainingMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    public TraineeServiceImpl(TraineeRepository trainerRepository, UserRepository userRepository, UserUtil userUtil,
                              PasswordEncoder encoder, TraineeMapper traineeMapper, TrainingMapper trainingMapper) {
        this.traineeRepository = trainerRepository;
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.encoder = encoder;
        this.traineeMapper = traineeMapper;
        this.trainingMapper = trainingMapper;
    }

    @Override
    @Transactional
    public CreateUserResponseDto createTrainee(CreateTraineeRequestDto dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already associated with an account");
        }
        String username = userUtil.generateUsername(dto.firstName(), dto.lastName(),
                userRepository.findAllUsernames());
        String firstPassword = userUtil.generateRandomPassword();
        Trainee trainee = traineeMapper.createTraineeRequestDtoToTrainee(dto);
        trainee.setUsername(username);
        trainee.setPassword(encoder.encode(firstPassword));
        trainee.setActive(true);
        trainee.setRole(Role.TRAINEE);
        traineeRepository.save(trainee);
        LOGGER.debug("The trainee {} was saved", username);
        return new CreateUserResponseDto(username, firstPassword);
    }

    @Override
    @Transactional(readOnly = true)
    public TraineeResponseDto selectTraineeByUsername(String username) {
        Trainee traineeSelected = getTraineeByUsername(username);
        LOGGER.debug("The trainee {} was select", traineeSelected.getUsername());
        return traineeMapper.traineeToTraineeResponseDto(traineeSelected);
    }

    @Override
    @Transactional
    public void deleteTrainee(String username) {
        Trainee traineeToDeleted = getTraineeByUsername(username);
        traineeRepository.delete(traineeToDeleted);
        LOGGER.debug("The trainee {} was deleted", traineeToDeleted.getUsername());
    }

    @Override
    @Transactional
    public TraineeUpdatedResponseDto updateTrainee(String username, UpdateTraineeRequestDto dto) {
        Trainee traineeToUpdated = getTraineeByUsername(username);
        traineeToUpdated.setFirstName(dto.firstName());
        traineeToUpdated.setLastName(dto.lastName());
        if (dto.dateOfBirth()!= null){
            traineeToUpdated.setDateOfBirth(dto.dateOfBirth());
        }
        if(dto.address()!= null &&
                !dto.address().isBlank()) {
            traineeToUpdated.setAddress(dto.address());
        }
        traineeToUpdated.setActive(dto.active());
        traineeRepository.save(traineeToUpdated);
        LOGGER.debug("The trainee {} was updated", traineeToUpdated.getUsername());
        return traineeMapper.traineeToTraineeUpdResponseDto(traineeToUpdated);
    }

    @Override
    @Transactional
    public void changeActiveStatus(String username, PatchUserRequestDto dto) {
        Trainee traineeToSwitchStatus = getTraineeByUsername(username);
        traineeToSwitchStatus.setActive(dto.active());
        traineeRepository.save(traineeToSwitchStatus);
        LOGGER.debug("Trainee '{}' active status changed to {}",
                traineeToSwitchStatus.getUsername(),
                traineeToSwitchStatus.getActive());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraineeTrainingsResponseDto> getTraineeTrainings(String username,
                                                                 TraineeTrainingsFilterRequestDto dto) {
        Trainee trainee = getTraineeByUsername(username);
        List <Training> filterTrainings = trainee.getTrainings().stream()
                .filter(t -> dto.periodFrom() == null || !t.getTrainingDate().isBefore(dto.periodFrom()))
                .filter(t -> dto.periodTo() == null ||  !t.getTrainingDate().isAfter((dto.periodTo())))
                .filter(t -> dto.trainerName() == null ||
                        t.getTrainer().getFirstName().equalsIgnoreCase(dto.trainerName()))
                .filter(t -> dto.trainingTypeId() == null ||
                        t.getTrainingType().getId().equals(dto.trainingTypeId()))
                .toList();
        LOGGER.info("The list of the trainee's training sessions was received with size {}",
                filterTrainings.size());
        filterTrainings.forEach(t ->
                LOGGER.debug("Training -> trainingName: {}, date: {}, duration: {} min," +
                            " trainee: {}, trainer: {}, trainerType: {}, trainingType: {}",
                        t.getTrainingName(), t.getTrainingDate(), t.getDurationInMinutes(),
                        t.getTrainee().getUsername(),t.getTrainer().getUsername(),
                        t.getTrainer().getSpecialization().getName(), t.getTrainingType().getName())
        );
        return trainingMapper.trainingsToTraineeTrainingsResponseDto(filterTrainings);
    }

    @Transactional(readOnly = true)
    private Trainee getTraineeByUsername(String username) {
        return traineeRepository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("Trainee was not found"));

    }
}
