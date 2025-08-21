package com.epam.service.impl;

import com.epam.dao.TrainerDao;
import com.epam.model.Trainer;
import com.epam.model.Training;
import com.epam.model.TrainingType;
import com.epam.security.PasswordEncoder;
import com.epam.service.AuthService;
import com.epam.service.TrainerService;
import com.epam.service.TrainingTypeService;
import com.epam.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao trainerDao;
    private final TrainingTypeService trainingTypeService;
    private final UserUtil userUtil;
    private final PasswordEncoder encoder;
    private final AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    public TrainerServiceImpl(TrainerDao trainerDao, TrainingTypeService trainingTypeService, UserUtil userUtil,
                              PasswordEncoder encoder, AuthService authService) {
        this.trainerDao = trainerDao;
        this.trainingTypeService = trainingTypeService;
        this.userUtil = userUtil;
        this.encoder = encoder;
        this.authService = authService;
    }

    @Override
    @Transactional
    public void createTrainer(String firstName, String lastName, Long trainingTypeId) {
        String username = userUtil.generateUsername(firstName, lastName, userUtil.getUserNames());

        TrainingType trainingType = trainingTypeService.selectTrainingTypeById(trainingTypeId);

        Trainer trainer = new Trainer(firstName, lastName, username,
                encoder.encode(userUtil.generateRandomPassword()),true,  trainingType);
        trainer = trainerDao.save(trainer);
        userUtil.addUsernames(username);
        LOGGER.debug("The {} was saved", trainer);

    }

    @Override
    @Transactional(readOnly = true)
    public Trainer selectTrainerByUsername(String username) {
        Trainer trainerSelected = getTrainerByUsername(username);
        LOGGER.debug("The {} was select", trainerSelected);
        return trainerSelected;
    }

    @Override
    @Transactional
    public void changePassword(String username, String currentPassword, String newPassword) {
        LOGGER.info("Initializing trainer login first");
        authService.authenticateTrainer(username, currentPassword);
        Trainer trainerToUpdate = selectTrainerByUsername(username);
        trainerToUpdate.setPassword(encoder.encode(newPassword));
        trainerDao.save(trainerToUpdate);
        LOGGER.debug("The password for {} was changed", username);
    }

    @Override
    @Transactional
    public void updateTrainer(String username, Long specializationId) {
        Trainer trainerToUpdated = getTrainerByUsername(username);
        trainerToUpdated.setSpecialization(trainingTypeService.selectTrainingTypeById(specializationId));
        trainerDao.save(trainerToUpdated);
        LOGGER.debug("The {} was updated", trainerToUpdated);
    }

    @Override
    @Transactional
    public void changeActiveStatus(String username) {
        Trainer trainerToSwitchStatus = getTrainerByUsername(username);
        boolean newStatus = !trainerToSwitchStatus.getActive();
        trainerToSwitchStatus.setActive(newStatus);
        trainerDao.save(trainerToSwitchStatus);
        LOGGER.debug("Trainer '{}' active status changed to {}", username, newStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainings(String username, LocalDate from, LocalDate to,
                                              String traineeName) {
        Trainer trainer = getTrainerByUsername(username);
        List <Training> filterTrainings = trainer.getTrainings().stream()
                .filter(t -> from == null || !t.getTrainingDate().isBefore(from))
                .filter(t -> to == null ||  !t.getTrainingDate().isAfter((to)))
                .filter(t -> traineeName == null ||
                        t.getTrainee().getFirstName().equalsIgnoreCase(traineeName))
                .toList();
        LOGGER.info("The list of the trainer's training sessions was received with size {}",
                filterTrainings.size());
        filterTrainings.forEach(t ->
                LOGGER.debug("Training -> trainingName: {}, date: {}, duration: {} min," +
                                " trainee: {}, trainer: {}, trainerType: {}, trainingType: {}",
                        t.getTrainingName(), t.getTrainingDate(), t.getDurationInMinutes(),
                        t.getTrainee().getUsername(),t.getTrainer().getUsername(),
                        t.getTrainer().getSpecialization().getName(), t.getTrainingType().getName())
        );
        return filterTrainings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trainer> getUnassignedTrainersByTraineeUsername(String traineeUsername) {
        List<Trainer> trainers = trainerDao.findUnassignedTrainersByTraineeUsername(traineeUsername);
        LOGGER.info("The list of the trainers was received with size {}",
                trainers.size());
        trainers.forEach( t ->
                LOGGER.debug("Trainer-> username: {}, type: {}", t.getUsername(),
                        t.getSpecialization().getName())
        );
        return trainers;
    }

    private Trainer getTrainerByUsername(String username) {
        return trainerDao.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Trainer was not found"));
    }

}
