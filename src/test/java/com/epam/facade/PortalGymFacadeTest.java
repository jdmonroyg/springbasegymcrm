//package com.epam.facade;
//
//import com.epam.config.ApplicationConfig;
//import com.epam.model.*;
//import jakarta.validation.ConstraintViolationException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @author jdmon on 30/07/2025
// * @project springbasegymcrm
// */
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationConfig.class)
//class PortalGymFacadeTest {
//
//    @Autowired
//    private PortalGymFacade portalGymFacade;
//
//    @Test
//    void createTraineeWithBadCredentials(){
//        assertThrows(ConstraintViolationException.class,
//                () -> portalGymFacade
//                        .createTrainee("", "",null,""));
//    }
//
//    @Test
//    void createTrainerWithBadCredentials(){
//        assertThrows(IllegalArgumentException.class,
//                () -> portalGymFacade
//                        .createTrainer("Juan", "Moreno",8L));
//    }
//
//    @Test
//    void initialTraineeChangePasswordAndLogin(){
//        assertDoesNotThrow(() -> portalGymFacade
//                .authenticateTrainee("Isabel.Miranda", "Ducks12345"));
//
//        assertDoesNotThrow(() -> portalGymFacade
//                .changeTraineePassword("Isabel.Miranda","Ducks12345",
//                        "Dogs123456"));
//
//        assertDoesNotThrow(() -> portalGymFacade
//                .authenticateTrainee("Isabel.Miranda", "Dogs123456"));
//    }
//
//    @Test
//    void initialTrainerChangePasswordAndLogin(){
//        assertDoesNotThrow(() -> portalGymFacade
//                .authenticateTrainer("Pablo.Chacon", "Ducks12345"));
//
//        assertDoesNotThrow(() -> portalGymFacade
//                .changeTrainerPassword("Pablo.Chacon","Ducks12345",
//                        "Dogs123456"));
//
//        assertDoesNotThrow(() -> portalGymFacade
//                .authenticateTrainer("Pablo.Chacon", "Dogs123456"));
//    }
//
//    @Test
//    void updateInitialTrainer(){
//        Trainer tBefore = portalGymFacade.selectTrainerByUsername("Pablo.Chacon");
//        assertEquals("Pilates", tBefore.getSpecialization().getName());
//
//        assertDoesNotThrow(() -> portalGymFacade
//                .updatedTrainer("Pablo.Chacon", 6L));
//        Trainer tAfter = portalGymFacade.selectTrainerByUsername("Pablo.Chacon");
//
//        assertEquals("Zumba", tAfter.getSpecialization().getName());
//    }
//
//    @Test
//    void updateInitialTrainee(){
//        Trainee tBefore = portalGymFacade.selectTraineeByUsername("isabel.miranda");
//        assertNull(tBefore.getDateOfBirth());
//        assertNull(tBefore.getAddress());
//
//        assertDoesNotThrow(() -> portalGymFacade
//                .updatedTrainee("isabel.miranda",
//                        LocalDate.of(1991,1,17),
//                        "Street 52c # 25 - 02"));
//        Trainee tAfter = portalGymFacade.selectTraineeByUsername("isabel.miranda");
//
//        assertEquals(LocalDate.of(1991,1,17),
//                tAfter.getDateOfBirth());
//        assertEquals("Street 52c # 25 - 02",
//                tAfter.getAddress());
//    }
//
//    @Test
//    void initialTraineeToggleTraineeActiveStatus(){
//        assertTrue(portalGymFacade.selectTraineeByUsername("isabel.miranda").getActive());
//        assertDoesNotThrow(() -> portalGymFacade
//                .toggleTraineeActiveStatus("isabel.miranda"));
//        assertFalse(portalGymFacade.selectTraineeByUsername("isabel.miranda").getActive());
//        assertDoesNotThrow(() -> portalGymFacade
//                .toggleTraineeActiveStatus("isabel.miranda"));
//        assertTrue(portalGymFacade.selectTraineeByUsername("isabel.miranda").getActive());
//    }
//
//    @Test
//    void initialTrainerToggleTrainerActiveStatus(){
//        assertTrue(portalGymFacade.selectTrainerByUsername("pablo.chacon").getActive());
//        assertDoesNotThrow(() -> portalGymFacade
//                .toggleTrainerActiveStatus("pablo.chacon"));
//        assertFalse(portalGymFacade.selectTrainerByUsername("pablo.chacon").getActive());
//        assertDoesNotThrow(() -> portalGymFacade
//                .toggleTrainerActiveStatus("pablo.chacon"));
//        assertTrue(portalGymFacade.selectTrainerByUsername("pablo.chacon").getActive());
//    }
//
//    @Test
//    void createTrainingsAndGetTrainingByUsername(){
//        assertDoesNotThrow(
//                () -> portalGymFacade
//                        .createTrainee("Jesus", "Monroy",null,""));
//        assertDoesNotThrow(
//                () -> portalGymFacade
//                        .createTrainee("Jesus", "Monroy",null,""));
//        assertDoesNotThrow(
//                () -> portalGymFacade
//                        .createTrainer("Juan", "Moreno",2L));
//
//        assertDoesNotThrow(()->
//                portalGymFacade.createTraining("isabel.miranda",
//                "pablo.chacon", "Zumba Class", 6L,
//                LocalDate.of(2025, 8, 25), 40));
//
//        assertDoesNotThrow(()->
//                portalGymFacade.createTraining("isabel.miranda",
//                "juan.moreno", "Cardio Class", 2L,
//                LocalDate.of(2025, 8, 24), 55));
//
//        assertDoesNotThrow(()->
//                portalGymFacade.createTraining("Jesus.Monroy",
//                "juan.moreno", "Cardio Class", 2L,
//                LocalDate.of(2025, 8, 23), 38));
//
//        assertEquals(2, portalGymFacade.getTraineeTrainings("isabel.miranda", null,
//                null, null, null).size());
//
//        assertEquals(1, portalGymFacade.getTrainerTrainings("pablo.chacon", null,
//                null, null).size());
//
//        assertDoesNotThrow(()->
//                portalGymFacade.deleteTrainee("isabel.miranda"));
//
//        assertThrows(IllegalArgumentException.class,
//                () -> portalGymFacade.getTraineeTrainings("isabel.miranda",null,
//                null,null,null));
//
//        List<Trainer> unassignedTrainers = portalGymFacade.getUnassignedTrainersByTraineeUsername("Jesus.monroy");
//        assertEquals(1, unassignedTrainers.size());
//
//        unassignedTrainers = portalGymFacade.getUnassignedTrainersByTraineeUsername("Jesus.monroy1");
//        assertEquals(2, unassignedTrainers.size());
//    }
//
//
//}