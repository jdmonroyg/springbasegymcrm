package com.epam.dao;

import com.epam.model.TrainingType;

import java.util.Optional;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
public interface TrainingTypeDao {
    Optional<TrainingType> findById(Long id);
}
