package com.epam.facade;

import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
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
public class PortalGymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalGymFacade.class);

    public PortalGymFacade(TraineeService traineeService,
                           TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public void createTrainee(String firstName, String lastName,
                              LocalDate dateOfBirth, String address){
        try{
            LOGGER.info("Entering a trainee");
            traineeService.createTrainee(firstName, lastName, dateOfBirth, address);
            LOGGER.info("A trainee was created");
        } catch (IllegalArgumentException e){
            LOGGER.error("createTrainee failed for {}", e.getMessage());
        }
    }

    public void updatedTrainee(long userId, LocalDate dateOfBirth, String address) {
        try {
            LOGGER.info("Updating a trainee");
            traineeService.updateTrainee(userId, dateOfBirth, address);
            LOGGER.info("A trainee was updated");
        } catch (IllegalArgumentException e){
            LOGGER.error("updatedTrainee failed for {}", e.getMessage());
        }

    }

    public void deleteTrainee(long userId){
        try {
            LOGGER.info("Deleting a trainee");
            traineeService.deleteTrainee(userId);
            LOGGER.info("A trainee was deleted");
        } catch (IllegalArgumentException e){
            LOGGER.error("deleteTrainee failed for {}", e.getMessage());
        }
    }

    public Trainee selectTrainee(long userId){
        try {
            LOGGER.info("Selecting a trainee");
            return traineeService.selectTrainee(userId);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Selecting trainee failed {}",e.getMessage());
        }
        return null;
    }

    public List<Trainee> selectAllTrainees(){
        LOGGER.info("Select all trainees");
        return traineeService.selectAllTrainees();
    }

    public void createTrainer(String firstName, String lastName, String specialization){
        try {
            LOGGER.info("Entering a trainer");
            trainerService.createTrainer(firstName,lastName,specialization);
            LOGGER.info("A trainer was created");
        } catch (IllegalArgumentException e) {
            LOGGER.error("createTrainer failed for {}", e.getMessage());
        }
    }

    public void updatedTrainer(long userId, String specialization){

        try {
            LOGGER.info("Updating a trainer");
            trainerService.updateTrainer(userId,specialization);
            LOGGER.info("A trainer was updated");
        } catch (IllegalArgumentException e){
            LOGGER.error("updatedTrainer failed for {}", e.getMessage());
        }
    }

    public Trainer selectTrainer(long userId){

        try {
            LOGGER.info("Selecting a trainer");
            return trainerService.selectTrainer(userId);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Selecting trainer failed {}",e.getMessage());
        }
        return null;
    }

    public List<Trainer> selectAllTrainers(){
        LOGGER.info("Select all trainers");
        return trainerService.selectAllTrainers();
    }

    public void createTraining (long traineeId, long trainerId, String trainingName,
                                String type, LocalDate trainingDate, int durationInMinutes){
        try {
            LOGGER.info("Entering a training");
            trainingService.createTraining(traineeId, trainerId, trainingName, type,
                    trainingDate, durationInMinutes);
            LOGGER.info("A training was created");
        } catch (IllegalArgumentException e) {
            LOGGER.error("createTraining failed for {}", e.getMessage());
        }
    }

    public Training selectTraining(long trainingId){
        try {
            LOGGER.info("Selecting a training");
            return trainingService.selectTraining(trainingId);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Selecting training failed {}",e.getMessage());
        }
        return null;
    }

    public List<Training> selectAllTrainings(){
        LOGGER.info("Select all trainings");
        return trainingService.selectAllTrainings();
    }

}
