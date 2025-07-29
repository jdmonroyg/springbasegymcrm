package com.epam.service;

import com.epam.model.Trainer;

import java.util.List;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
public interface TrainerService {
    void createTrainer(String firstName, String lastName, String specialization);
    void updateTrainer(long userId, String specialization);
    Trainer selectTrainer(long id);
    List<Trainer> selectAllTrainers();
}
