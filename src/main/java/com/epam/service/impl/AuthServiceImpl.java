package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.dao.TrainerDao;
import com.epam.model.Trainee;
import com.epam.model.Trainer;
import com.epam.security.PasswordEncoder;
import com.epam.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final TraineeDao traineeDao;
    private final TrainerDao trainerDao;
    private final PasswordEncoder encoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(TraineeDao traineeDao, TrainerDao trainerDao, PasswordEncoder encoder) {
        this.traineeDao = traineeDao;
        this.trainerDao = trainerDao;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public void authenticateTrainee(String username, String rawPassword) {
        Trainee traineeSelected = traineeDao.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Unauthorized trainee: bad credentials"));
        Boolean matches = encoder.matches(rawPassword, traineeSelected.getPassword());
        LOGGER.debug("Authentication result for trainee '{}': {}", username, matches);
        if (!matches) {
            LOGGER.warn("Authentication trainee failed: bad credentials");
            throw new RuntimeException("Unauthorized trainee: invalid credentials");
        }
        LOGGER.info("Authentication for trainee was successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public void authenticateTrainer(String username, String rawPassword) {
        Trainer trainerSelected = trainerDao.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Unauthorized trainer: bad credentials"));
        Boolean matches = encoder.matches(rawPassword, trainerSelected.getPassword());
        LOGGER.debug("Authentication result for trainer '{}': {}", username, matches);
        if (!matches) {
            LOGGER.warn("Authentication trainer failed: bad credentials");
            throw new RuntimeException("Unauthorized trainer: invalid credentials");
        }
        LOGGER.info("Authentication for trainer was successfully");
    }
}
