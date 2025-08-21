package com.epam.facade;

import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.AuthService;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

import static com.epam.util.Constants.*;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
@Service
@Validated
public class PortalGymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalGymFacade.class);

    public PortalGymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService, AuthService authService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.authService = authService;
    }

    public void createTrainer(@NotBlank (message = INVALID_FIELD)
                              String firstName,
                              @NotBlank (message = INVALID_FIELD)
                              String lastName,
                              @Positive (message = INVALID_ID_VALUE)
                              Long trainingTypeId){
        LOGGER.info("Creating a trainer");
        trainerService.createTrainer(firstName, lastName, trainingTypeId);
        LOGGER.info("A trainer was created");
    }

    public void createTrainee(@NotBlank (message = INVALID_FIELD)
                              String firstName,
                              @NotBlank (message = INVALID_FIELD)
                              String lastName,
                              LocalDate dateOfBirth, String address){
        LOGGER.info("creating a trainee");
        traineeService.createTrainee(firstName, lastName, dateOfBirth, address);
        LOGGER.info("A trainee was created");
    }

    public void authenticateTrainee(@NotBlank (message = INVALID_FIELD)
                                       String username,
                                       @NotBlank (message = INVALID_FIELD)
                                       String password){
        LOGGER.info("Initializing trainee login");
        authService.authenticateTrainee(username, password);
    }

    public void authenticateTrainer(@NotBlank (message = INVALID_FIELD)
                                       String username,
                                       @NotBlank (message = INVALID_FIELD)
                                       String password){
        LOGGER.info("Initializing trainer login");
        authService.authenticateTrainer(username, password);
    }

    public Trainee selectTraineeByUsername(@NotBlank (message = INVALID_FIELD)
                                           String username){
        LOGGER.info("Selecting a trainee by username");
        return traineeService.selectTraineeByUsername(username);
    }

    public Trainer selectTrainerByUsername(@NotBlank (message = INVALID_FIELD)
                                           String username){
        LOGGER.info("Selecting a trainer by username");
        return trainerService.selectTrainerByUsername(username);
    }

    public void changeTraineePassword(@NotBlank (message = INVALID_FIELD)
                                      String username,
                                      @NotBlank (message = INVALID_FIELD)
                                      String currentPassword,
                                      @NotBlank (message = INVALID_FIELD)
                                      String newPassword){
        LOGGER.info("Initializing Trainee Password Change");
        traineeService.changePassword(username, currentPassword, newPassword);
    }

    public void changeTrainerPassword(@NotBlank (message = INVALID_FIELD)
                                      String username,
                                      @NotBlank (message = INVALID_FIELD)
                                      String currentPassword,
                                      @NotBlank (message = INVALID_FIELD)
                                      String newPassword){
        LOGGER.info("Initializing Trainer Password Change");
        trainerService.changePassword(username, currentPassword, newPassword);
    }

    public void updatedTrainer(@NotBlank (message = INVALID_FIELD)
                               String username,
                               @Positive (message = INVALID_ID_VALUE)
                               Long specializationId){
        trainerService.updateTrainer(username, specializationId);
        LOGGER.info("A trainer was updated");
    }

    public void updatedTrainee(@NotBlank (message = INVALID_FIELD)
                               String username,
                               @NotNull (message = INVALID_FIELD)
                               LocalDate dateOfBirth,
                               @NotNull (message = INVALID_FIELD)
                               String address) {

        LOGGER.info("Updating a trainee");
        traineeService.updateTrainee(username, dateOfBirth, address);
        LOGGER.info("A trainee was updated");
    }

    public void toggleTraineeActiveStatus(@NotBlank (message = INVALID_FIELD)
                                          String username){
        LOGGER.info("Initializing Activate/De-Active a trainee");
        traineeService.changeActiveStatus(username);
        LOGGER.info("The status of a trainee changed");
    }

    public void toggleTrainerActiveStatus(@NotBlank (message = INVALID_FIELD)
                                          String username) {
        LOGGER.info("Initializing Activate/De-Active a trainer");
        trainerService.changeActiveStatus(username);
        LOGGER.info("The status of a trainer changed");

    }

    public void deleteTrainee(@NotBlank (message = INVALID_FIELD)
                              String username){
        LOGGER.info("Deleting a trainee");
        traineeService.deleteTrainee(username);
        LOGGER.info("A trainee was deleted");
    }

    public void createTraining (@NotBlank (message = INVALID_FIELD)
                                String traineeUsername,
                                @NotBlank (message = INVALID_FIELD)
                                String trainerUsername,
                                @NotBlank (message = INVALID_FIELD)
                                String trainingName,
                                @Positive (message = INVALID_ID_VALUE)
                                Long trainingType,
                                @NotNull
                                LocalDate trainingDate,
                                @Min(value = 20, message = INVALID_MIN_VALUE)
                                @Max(value = 60, message = INVALID_MAX_VALUE)
                                int durationInMinutes){
        LOGGER.info("Entering a training");
        trainingService.createTraining(traineeUsername, trainerUsername, trainingName, trainingType,
                trainingDate, durationInMinutes);
        LOGGER.info("A training was created");
    }

    public List<Training> getTraineeTrainings(@NotBlank (message = INVALID_FIELD)
                                              String username, LocalDate from, LocalDate to,
                                              String trainerName, Long trainingTypeId){
        LOGGER.info("Getting trainee training list");
        return traineeService.getTraineeTrainings( username, from, to, trainerName, trainingTypeId);
    }

    public List<Training> getTrainerTrainings(@NotBlank (message = INVALID_FIELD)
                                              String username, LocalDate from, LocalDate to,
                                              String traineeName){
        LOGGER.info("Getting trainer training list");
        return trainerService.getTrainerTrainings( username, from, to, traineeName);
    }

    public List<Trainer> getUnassignedTrainersByTraineeUsername(@NotBlank (message = INVALID_FIELD)
                                                                String traineeUsername){
        LOGGER.info("Getting a Unassigned Trainers by Trainee Username");
        return trainerService.getUnassignedTrainersByTraineeUsername(traineeUsername);

    }


}
