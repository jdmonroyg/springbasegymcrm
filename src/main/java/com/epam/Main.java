package com.epam;

import com.epam.config.ApplicationConfig;
import com.epam.facade.PortalGymFacade;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 25/07/2025
 * @project Default (Template) Project
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(
                             ApplicationConfig.class)) {
            LOGGER.info("Initializing project");
            PortalGymFacade portalGymFacade = ctx.getBean(PortalGymFacade.class);

            LOGGER.info("----- Creating Trainee with bad parameters -----");
            portalGymFacade.createTrainee("", "",null,"");

            LOGGER.info("----- Creating two trainee with same names -----");
            portalGymFacade.createTrainee("Jesus", "Monroy",null,"");
            portalGymFacade.createTrainee("Jesus", "Monroy",null,"");

            LOGGER.info("----- Selecting a trainee jesus.monroy -----");
            Trainee trainee1 = portalGymFacade.selectTraineeByUsername("jesus.monroy");

            LOGGER.info("----- Creating two trainers, one with good parameters and one with bad parameters -----");
            portalGymFacade.createTrainer("Juan", "Moreno",2L);
            portalGymFacade.createTrainer("Juan", "Moreno",8L);

            LOGGER.info("----- Selecting a trainee juan.moreno -----");
            Trainer trainer1 = portalGymFacade.selectTrainerByUsername("juan.moreno");

            LOGGER.info("----- Login for a trainee and a trainer -----");
            portalGymFacade.authenticateTrainee("Isabel.Miranda", "Ducks12345");
            portalGymFacade.authenticateTrainer("Pablo.Chacon", "Ducks12345");

            LOGGER.info("----- Changing password with authentication for trainer and trainee -----");
            portalGymFacade.changeTraineePassword("Isabel.Miranda","Ducks12345",
                    "Dogs123456");
            portalGymFacade.changeTrainerPassword("Pablo.Chacon","Ducks12345",
                    "Dogs123456");

            LOGGER.info("----- Login for a trainee and a trainer with the new password -----");
            portalGymFacade.authenticateTrainee("Isabel.Miranda", "Dogs123456");
            portalGymFacade.authenticateTrainer("Pablo.Chacon", "Dogs123456");

            LOGGER.info("----- Updating a trainer, changing their specialization -----");
            portalGymFacade.updatedTrainer("Pablo.Chacon",6L);

            LOGGER.info("----- Updating a trainee, completing their birthday and address -----");
            portalGymFacade.updatedTrainee("isabel.miranda",
                    LocalDate.of(1991,1,17), "Street 52c # 25 - 02");

            LOGGER.info("----- Changing status for a trainee and a trainer -----");
            portalGymFacade.toggleTraineeActiveStatus("isabel.miranda");
            portalGymFacade.toggleTrainerActiveStatus("pablo.chacon");

            LOGGER.info("----- Changing status for a trainee and a trainer -----");
            portalGymFacade.toggleTraineeActiveStatus("isabel.miranda");
            portalGymFacade.toggleTrainerActiveStatus("pablo.chacon");

            LOGGER.info("----- Creating 3 trainings -----");
            portalGymFacade.createTraining("isabel.miranda",
                    "pablo.chacon", "Zumba Class", 6L,
                    LocalDate.of(2025, 8, 25), 40);

            portalGymFacade.createTraining("isabel.miranda",
                    "juan.moreno", "Cardio Class", 2L,
                    LocalDate.of(2025, 8, 24), 55);

            portalGymFacade.createTraining("Jesus.Monroy",
                    "juan.moreno", "Cardio Class", 2L,
                    LocalDate.of(2025, 8, 23), 38);

//            LOGGER.info("----- Deleting a trainee with two trainings (1, 2) -----");
//            portalGymFacade.deleteTrainee("isabel.miranda");

            LOGGER.info("----- Getting trainings for isabel.miranda -----");
            List<Training> traineeTrainings = portalGymFacade.getTraineeTrainings("isabel.miranda",null,
                    null,null,null);
            traineeTrainings.forEach(System.out::println);

            LOGGER.info("----- Getting trainings for Jesus.Monroy -----");
            List<Training> trainerTrainings = portalGymFacade.getTrainerTrainings("juan.moreno",null,
                    null,null);

            trainerTrainings.forEach(System.out::println);

            LOGGER.info("----- Deleting a trainee with two trainings (1, 2) -----");
            portalGymFacade.deleteTrainee("isabel.miranda");

            LOGGER.info("----- Receiving training from the deleted Trainee Isabel.miranda -----");
            portalGymFacade.getTraineeTrainings("isabel.miranda",null,
                    null,null,null);

            LOGGER.info("----- Getting the trainers not assigned to the Trainee jesus.monroy -----");
            List<Trainer> unassignedTrainers = portalGymFacade.getUnassignedTrainersByTraineeUsername("Jesus.monroy");
            unassignedTrainers.forEach(System.out::println);

            LOGGER.info("----- Getting the trainers not assigned to the Trainee jesus.monroy1 -----");
            unassignedTrainers = portalGymFacade.getUnassignedTrainersByTraineeUsername("Jesus.monroy1");
            unassignedTrainers.forEach(System.out::println);

        }
    }
}