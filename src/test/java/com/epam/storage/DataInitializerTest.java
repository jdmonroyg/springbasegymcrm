package com.epam.storage;

import com.epam.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 29/07/2025
 * @project springbasegymcrm
 */
public class DataInitializerTest {

    private TraineeStorage traineeStorage;
    private TrainerStorage trainerStorage;
    private TrainingStorage trainingStorage;

    private DataInitializer dataInitializer;

    @BeforeEach
    void setup() throws Exception {
        traineeStorage = new TraineeStorage();
        trainerStorage = new TrainerStorage();
        trainingStorage = new TrainingStorage();
        UserUtil userUtil = new UserUtil();

        dataInitializer = new DataInitializer(traineeStorage,trainerStorage,trainingStorage);
        dataInitializer.setUserUtil(userUtil);

        Field field = DataInitializer.class.getDeclaredField("initFilePath");
        field.setAccessible(true);
        field.set(dataInitializer, "data/init-data.json");
    }

    @Test
    void initTest() {
        dataInitializer.init();
        assertEquals(2, traineeStorage.getTraineeMap().size());
        assertEquals(2, trainerStorage.getTrainerMap().size());
        assertEquals(1, trainingStorage.getTrainingMap().size());
    }
}