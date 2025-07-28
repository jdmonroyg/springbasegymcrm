package com.epam.service;

import com.epam.model.Trainee;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
public interface TraineeService {
    void createTrainee(Trainee trainee);
    void updateTrainee(Trainee trainee);
    void deleteTrainee(long id);
    Trainee selectTrainee(long id);
}
