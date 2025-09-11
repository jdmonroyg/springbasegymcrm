package com.epam.repository;

import com.epam.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {
}