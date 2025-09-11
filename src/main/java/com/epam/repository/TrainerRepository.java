package com.epam.repository;

import com.epam.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUsername(String username);

    @Query("""
            SELECT tr FROM Trainer tr
            WHERE tr.active = true
            AND tr.id NOT IN (
                SELECT t.trainer.id
                FROM Training t
                WHERE LOWER(t.trainee.username) = LOWER(:username)
            )
            """)
    List<Trainer> findActiveUnassignedTrainersByTraineeUsername(@Param("username")String traineeUsername);
}