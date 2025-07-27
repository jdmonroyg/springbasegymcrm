package com.epam;

import com.epam.config.ApplicationConfig;
import com.epam.storage.DataInitializer;
import com.epam.storage.TraineeStorage;
import com.epam.storage.TrainerStorage;
import com.epam.storage.TrainingStorage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author jdmon on 25/07/2025
 * @project Default (Template) Project
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        DataInitializer init = context.getBean(DataInitializer.class);
        TraineeStorage traineeStorage = context.getBean(TraineeStorage.class);
        TrainerStorage trainerStorage = context.getBean(TrainerStorage.class);
        TrainingStorage trainingStorage = context.getBean(TrainingStorage.class);

        trainerStorage.getTrainerMap().forEach((key, trainer) ->
                System.out.println("key: "+key+" Value: "+trainer));

        traineeStorage.getTraineeMap().forEach((key, trainee) ->
                System.out.println("key: "+key+" Value: "+trainee));

        trainingStorage.getTrainingeMap().forEach((key, training)->
                System.out.println("key: "+key+" Value: "+training));





    }
}