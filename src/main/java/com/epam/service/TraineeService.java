package com.epam.service;

import com.epam.model.Trainee;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
public interface TraineeService {
    void createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address);
    void updateTrainee(long userId, LocalDate dateOfBirth, String address);
    void deleteTrainee(long id);
    Trainee selectTrainee(long id);
    List<Trainee> selectAllTrainees();
}
