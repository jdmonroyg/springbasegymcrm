package com.epam.dao;

import com.epam.model.Trainer;

import java.util.List;
import java.util.Optional;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TrainerDao {
    Trainer save(Trainer trainer);
    Optional<Trainer> findByUsername(String username);
    List<Trainer> findUnassignedTrainersByTraineeUsername(String traineeUsername);
}
