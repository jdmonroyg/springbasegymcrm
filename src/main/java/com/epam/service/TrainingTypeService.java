package com.epam.service;

import com.epam.model.TrainingType;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
public interface TrainingTypeService {

    TrainingType selectTrainingTypeById(Long id);
}
