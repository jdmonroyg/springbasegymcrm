package com.epam.service.impl;

import com.epam.dao.TrainingTypeDao;
import com.epam.model.TrainingType;
import com.epam.service.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeDao trainingTypeDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    public TrainingTypeServiceImpl(TrainingTypeDao trainingTypeDao) {
        this.trainingTypeDao = trainingTypeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public TrainingType selectTrainingTypeById(Long id) {
        TrainingType trainingTypeSelected = trainingTypeDao.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("TrainingType was not found"));
        LOGGER.debug("The {} was select", trainingTypeSelected);
        return trainingTypeSelected;
    }
}
