package com.epam.repository;

import com.epam.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    Optional<Trainee> findByUsername(String username);
}