package com.epam.service;

import com.epam.model.Training;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
public interface TrainingService {
    void createTraining(Training training);
    Training selectTraining(long id);
}
