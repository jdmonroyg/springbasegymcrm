package com.epam.repository;

import com.epam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("Select u.username from User u")
    Set<String> findAllUsernames();
}