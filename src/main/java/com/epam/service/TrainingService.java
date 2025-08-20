package com.epam.service;

import java.time.LocalDate;

/**
 * @author jdmon on 17/08/2025
 * @project springbasegymcrm
 */
public interface TrainingService {

    void createTraining(String traineeUsername, String trainerUsername, String trainingName,
                        Long trainingType, LocalDate trainingDate, int durationInMinutes);
}
