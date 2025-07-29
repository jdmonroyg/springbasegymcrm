package com.epam.facade;

import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.service.TraineeService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingService;
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

    public PortalGymFacade(TraineeService traineeService,
                           TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public void createTrainee(String firstName, String lastName,
                              LocalDate dateOfBirth, String address){
        traineeService.createTrainee(firstName, lastName, dateOfBirth, address);
    }

    public void updatedTrainee(long userId, LocalDate dateOfBirth, String address) {
        traineeService.updateTrainee(userId, dateOfBirth, address);
    }

    public void deleteTrainee(long userId){
        traineeService.deleteTrainee(userId);
    }

    public Trainee selectTrainee(long userId){
        return traineeService.selectTrainee(userId);
    }

    public List<Trainee> selectAllTrainees(){
        return traineeService.selectAllTrainees();
    }

    public void createTrainer(String firstName, String lastName, String specialization){
        trainerService.createTrainer(firstName,lastName,specialization);
    }

    public void updatedTrainer(long userId, String specialization){
        trainerService.updateTrainer(userId,specialization);
    }

    public Trainer selectTrainer(long userId){
        return trainerService.selectTrainer(userId);
    }

    public List<Trainer> selectAllTrainers(){
        return trainerService.selectAllTrainers();
    }

    public void createTraining (long traineeId, long trainerId, String trainingName,
                                String type, LocalDate trainingDate, int trainingDuration){
        trainingService.createTraining(traineeId, trainerId, trainingName, type,
                trainingDate, trainingDuration);
    }

    public Training selectTraining(long trainingId){
        return trainingService.selectTraining(trainingId);
    }

    public List<Training> selectAllTrainings(){
        return trainingService.selectAllTrainings();
    }


}
