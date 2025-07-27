package com.epam.storage;

import com.epam.model.Trainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
@Component
public class TrainerStorage {
    private final Map<Long, Trainer> trainerMap = new HashMap<>();

    public Map<Long, Trainer> getTrainerMap(){
        return trainerMap;
    }

}
