package com.epam.storage;

import com.epam.model.Training;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Component
public class TrainingStorage {
    private final Map<Long, Training> trainingMap = new HashMap<>();

    public Map<Long, Training> getTrainingeMap(){
        return trainingMap;
    }

}
