package com.epam.storage;

import com.epam.model.Training;
import com.epam.model.User;
import com.epam.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Component
public class DataInitializer {

    @Value("${storage.init.file}")
    private String initFilePath;

    private final TraineeStorage traineeStorage;
    private final TrainerStorage trainerStorage;
    private final TrainingStorage trainingStorage;

    private UserUtil userUtil;

    public DataInitializer(TraineeStorage traineeStorage,
                           TrainerStorage trainerStorage, TrainingStorage trainingStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
        this.trainingStorage = trainingStorage;
    }

    @Autowired
    public void setUserUtil(UserUtil userUtil){
        this.userUtil=userUtil;
    }

    @PostConstruct
    public void init(){
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(initFilePath)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            InitData data= mapper.readValue(is, InitData.class);

            data.getTrainees().forEach(trainee ->{
                traineeStorage.getTraineeMap().put(trainee.getUserId(),trainee);
                userUtil.addUsernames(trainee.getUserName());
            });
            data.getTrainers().forEach(trainer ->{
                trainerStorage.getTrainerMap().put(trainer.getUserId(),trainer);
                userUtil.addUsernames(trainer.getUserName());
            });
            data.getTrainings().forEach(training ->
                    trainingStorage.getTrainingMap().put(training.getTrainingId(),training));

            User.initializeNextId(Math.max(data.getTrainees().stream().mapToLong(User::getUserId).max().orElse(0),
            data.getTrainers().stream().mapToLong(User::getUserId).max().orElse(0)));

            Training.initializeNextId(data.getTrainings().stream().mapToLong(Training::getTrainingId).max().orElse(0));

            System.out.println("The data was fully loaded");

        } catch (IOException e){
            System.err.println("Error in initialization data: " + e.getMessage());
        }
    }
}
