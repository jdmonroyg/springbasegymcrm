package com.epam.service;

import com.epam.model.Trainee;
import com.epam.model.Training;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 15/08/2025
 * @project springbasegymcrm
 */
public interface TraineeService {

    void createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address);
    Trainee selectTraineeByUsername(String username);
    void deleteTrainee(String username);
    void changePassword(String username, String currentPassword, String newPassword);
    void updateTrainee(String username, LocalDate dateOfBirth, String address);
    void changeActiveStatus(String username);
    List<Training> getTraineeTrainings(String username, LocalDate from, LocalDate to,
                                       String trainerName, Long trainingTypeId);
}
