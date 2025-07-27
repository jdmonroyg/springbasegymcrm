package com.epam.storage;

import com.epam.model.Trainee;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Component
public class TraineeStorage {
    private final Map<Long, Trainee> traineeMap = new HashMap<>();

    public Map<Long, Trainee> getTraineeMap(){
        return traineeMap;
    }

}
