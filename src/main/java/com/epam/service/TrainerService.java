package com.epam.service;

import com.epam.model.Trainer;
import com.epam.model.Training;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
public interface TrainerService {
    void createTrainer(String firstName, String lastName, Long trainingTypeId);
    Trainer selectTrainerByUsername(String username);
    void changePassword(String username, String currentPassword, String newPassword);
    void updateTrainer(String username, Long specializationId);
    void changeActiveStatus(String username);
    List<Training> getTrainerTrainings(String username, LocalDate from, LocalDate to, String traineeName);
    List<Trainer> getUnassignedTrainersByTraineeUsername(String traineeUsername);
}
