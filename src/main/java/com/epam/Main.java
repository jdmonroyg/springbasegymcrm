package com.epam;

import com.epam.config.ApplicationConfig;
import com.epam.facade.PortalGymFacade;
import com.epam.storage.TraineeStorage;
import com.epam.storage.TrainerStorage;
import com.epam.storage.TrainingStorage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

/**
 * @author jdmon on 25/07/2025
 * @project Default (Template) Project
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        TraineeStorage traineeStorage = context.getBean(TraineeStorage.class);
        TrainerStorage trainerStorage = context.getBean(TrainerStorage.class);
        TrainingStorage trainingStorage = context.getBean(TrainingStorage.class);

        trainerStorage.getTrainerMap().forEach((key, trainer) ->
                System.out.println("key: "+key+" Value: "+trainer));

        traineeStorage.getTraineeMap().forEach((key, trainee) ->
                System.out.println("key: "+key+" Value: "+trainee));

        trainingStorage.getTrainingMap().forEach((key, training)->
                System.out.println("key: "+key+" Value: "+training));

        PortalGymFacade portalGymFacade = context.getBean(PortalGymFacade.class);
        portalGymFacade.createTrainee("Jesus", "Monroy",
                LocalDate.of(1993, 2,19),"Street 52c #85c - 20");
        portalGymFacade.createTrainee("Jesus", "Monroy",
                LocalDate.of(1994,8,1),"Street 72c #32c - 22");

        portalGymFacade.updatedTrainee(6,LocalDate.of(1995,8,1),"Street 72c #32c - 23");

        System.out.println(portalGymFacade.selectTrainee(6));
        System.out.println(portalGymFacade.selectTrainee(10));
        portalGymFacade.deleteTrainee(6);
        //System.out.println(portalGymFacade.selectAllTrainees());

        System.out.println(portalGymFacade.selectAllTrainers());
        portalGymFacade.createTrainer("Daniel", "Monroy","zumba");
        System.out.println(portalGymFacade.selectAllTrainees());
        System.out.println(portalGymFacade.selectAllTrainers());

        portalGymFacade.createTraining(3, 7, "Colombian Zumba",
                "zumba", LocalDate.of(2025,8,2),60);

        System.out.println(portalGymFacade.selectAllTrainings());

        System.out.println(portalGymFacade.selectTraining(2));




    }
}