package com.epam.facade;

import com.epam.dao.TraineeDao;
import com.epam.dao.TrainerDao;
import com.epam.dao.TrainingDao;
import com.epam.dao.impl.TraineeDaoImpl;
import com.epam.dao.impl.TrainerDaoImpl;
import com.epam.dao.impl.TrainingDaoImpl;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.service.TrainingService;
import com.epam.service.impl.TraineeServiceImpl;
import com.epam.service.impl.TrainerServiceImpl;
import com.epam.service.impl.TrainingServiceImpl;
import com.epam.storage.TraineeStorage;
import com.epam.storage.TrainerStorage;
import com.epam.storage.TrainingStorage;
import com.epam.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 30/07/2025
 * @project springbasegymcrm
 */
class PortalGymFacadeTest {

    private PortalGymFacade portalGymFacade;

    @BeforeEach
    void setup(){
        //Storage
        TraineeStorage traineeStorage = new TraineeStorage();
        TrainerStorage trainerStorage = new TrainerStorage();
        TrainingStorage trainingStorage = new TrainingStorage();


        //Dao
        TraineeDao traineeDao = new TraineeDaoImpl(traineeStorage);
        TrainerDao trainerDao = new TrainerDaoImpl(trainerStorage);
        TrainingDao trainingDao = new TrainingDaoImpl(trainingStorage);

        //Util
        UserUtil userUtil = new UserUtil();

        //Service
        TraineeServiceImpl traineeService = new TraineeServiceImpl(traineeDao);
        traineeService.setUserUtil(userUtil);
        TrainerServiceImpl trainerService = new TrainerServiceImpl(trainerDao);
        trainerService.setUserUtil(userUtil);
        TrainingService trainingService = new TrainingServiceImpl(trainingDao,traineeService,trainerService);

        portalGymFacade = new PortalGymFacade(traineeService,trainerService,trainingService);
    }


    @Test
    void createTraining() {
        portalGymFacade.createTrainee("Jesus", "Monroy",
                LocalDate.of(1993, 2,19),"Street 52c #85c - 20");
        portalGymFacade.createTrainee("Jesus", "Monroy",
                LocalDate.of(1994,8,1),"Street 72c #32c - 22");
        portalGymFacade.createTrainee("Jesus", "Monroy",
                LocalDate.of(1998,3,1),"Street 7c #82c - 20");
        portalGymFacade.createTrainee("Pablo", "Chacon",
                LocalDate.of(1984,2,1),"Street 7c #2c - 28");
        List<Trainee> trainees = portalGymFacade.selectAllTrainees();

        assertEquals(4,trainees.size());
        Trainee trainee3 = portalGymFacade.selectTrainee(7);
        assertEquals("Jesus.Monroy2",trainee3.getUserName());

        portalGymFacade.updatedTrainee(7,LocalDate.of(1995,2,1),"Street 7c #22c - 23");
        trainee3 = portalGymFacade.selectTrainee(7);

        assertEquals("Street 7c #22c - 23",trainee3.getAddress());

        Trainee trainee2 = portalGymFacade.selectTrainee(6);
        portalGymFacade.deleteTrainee(6);
        assertFalse(trainee2.isActive());

        portalGymFacade.createTrainer("Daniel", "Monroy","zumba");
        portalGymFacade.createTrainer("Juan", "Garzon","yoga");
        portalGymFacade.createTrainer("Nestor", "Herrera","STRENGTH");
        List<Trainer> trainers = portalGymFacade.selectAllTrainers();
        assertEquals(3,trainers.size());

        portalGymFacade.updatedTrainer(11,"PILATES");
        Trainer trainer7 = portalGymFacade.selectTrainer(11);
        assertEquals(TrainingType.PILATES, trainer7.getSpecialization());


        portalGymFacade.createTraining(5, 9, "Colombian Zumba",
                "zumba", LocalDate.of(2025,8,2),60);
        portalGymFacade.createTraining(8, 10, "Colombian Yoga",
                "yoga", LocalDate.of(2025,8,2),30);
        List<Training> trainings = portalGymFacade.selectAllTrainings();
        assertEquals(2, trainings.size());
        Training training1 = portalGymFacade.selectTraining(2);
        Training training2 = portalGymFacade.selectTraining(3);
        assertEquals("Colombian Zumba",training1.getTrainingName());
        assertEquals(TrainingType.ZUMBA,training1.getType());

        assertEquals("Colombian Yoga",training2.getTrainingName());
        assertEquals(TrainingType.YOGA,training2.getType());

    }
}