package com.epam.repository;

import com.epam.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
}