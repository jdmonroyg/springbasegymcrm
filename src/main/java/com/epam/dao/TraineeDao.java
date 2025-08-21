package com.epam.dao;

import com.epam.model.Trainee;

import java.util.Optional;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TraineeDao {
    Trainee save(Trainee trainee);

    Optional<Trainee> findByUsername(String username);

    void deletedByUsername(Trainee trainee);
}
