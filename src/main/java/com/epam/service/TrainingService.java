package com.epam.service;

import com.epam.model.Training;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
public interface TrainingService {
    void createTraining(long traineeId, long trainerId, String trainingName, String type,
                        LocalDate trainingDate, int trainingDuration);
    Training selectTraining(long id);
    List<Training> selectAllTrainings();
}
