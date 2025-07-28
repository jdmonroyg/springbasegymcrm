package com.epam.service;

import com.epam.model.Trainer;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
public interface TrainerService {
    void createTrainer(Trainer trainer);
    void updateTrainer(Trainer trainer);
    Trainer selectTrainer(long id);
}
