package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import com.epam.model.Training;
import com.epam.security.PasswordEncoder;
import com.epam.service.AuthService;
import com.epam.service.TraineeService;
import com.epam.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 15/08/2025
 * @project springbasegymcrm
 */
@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDao traineeDao;
    private final UserUtil userUtil;
    private final PasswordEncoder encoder;
    private final AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    public TraineeServiceImpl(TraineeDao traineeDao, UserUtil userUtil, PasswordEncoder encoder, AuthService authService) {
        this.traineeDao = traineeDao;
        this.userUtil = userUtil;
        this.encoder = encoder;
        this.authService = authService;
    }

    @Override
    @Transactional
    public void createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        String username = userUtil.generateUsername(firstName, lastName, userUtil.getUserNames());
        Trainee trainee = new Trainee(firstName, lastName, username,
                encoder.encode(userUtil.generateRandomPassword()),true, dateOfBirth, address );
        trainee = traineeDao.save(trainee);
        userUtil.addUsernames(trainee.getUsername());
        LOGGER.debug("The {} was saved", trainee);
    }

    @Override
    @Transactional(readOnly = true)
    public Trainee selectTraineeByUsername(String username) {
        Trainee traineeSelected = getTraineeByUsername(username);
        LOGGER.debug("The {} was select", traineeSelected);
        return traineeSelected;
    }

    @Override
    @Transactional
    public void deleteTrainee(String username) {
        Trainee traineeToDeleted = getTraineeByUsername(username);
        traineeDao.deletedByUsername(traineeToDeleted);
        LOGGER.debug("The {} was deleted", traineeToDeleted);
    }

    @Override
    @Transactional
    public void changePassword(String username, String currentPassword, String newPassword) {
        LOGGER.info("Initializing trainee login first");
        authService.authenticateTrainee(username, currentPassword);
        Trainee traineeToUpdate = selectTraineeByUsername(username);
        traineeToUpdate.setPassword(encoder.encode(newPassword));
        traineeDao.save(traineeToUpdate);
        LOGGER.debug("The password for {} was changed", username);
    }

    @Override
    @Transactional
    public void updateTrainee(String username, LocalDate dateOfBirth, String address) {
        Trainee traineeToUpdated = getTraineeByUsername(username);
        traineeToUpdated.setDateOfBirth(dateOfBirth);
        traineeToUpdated.setAddress(address);
        traineeDao.save(traineeToUpdated);
        LOGGER.debug("The {} was updated", traineeToUpdated);

    }

    @Override
    @Transactional
    public void changeActiveStatus(String username) {
        Trainee traineeToSwitchStatus = getTraineeByUsername(username);
        boolean newStatus = !traineeToSwitchStatus.getActive();
        traineeToSwitchStatus.setActive(newStatus);
        traineeDao.save(traineeToSwitchStatus);
        LOGGER.debug("Trainee '{}' active status changed to {}", username, newStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainings(String username, LocalDate from, LocalDate to,
                                              String trainerName, Long trainingTypeId) {
        Trainee trainee = getTraineeByUsername(username);
        List <Training> filterTrainings = trainee.getTrainings().stream()
                .filter(t -> from == null || !t.getTrainingDate().isBefore(from))
                .filter(t -> to == null ||  !t.getTrainingDate().isAfter((to)))
                .filter(t -> trainerName == null ||
                        t.getTrainer().getFirstName().equalsIgnoreCase(trainerName))
                .filter(t -> trainingTypeId == null ||
                        t.getTrainingType().getId().equals(trainingTypeId))
                .toList();
        LOGGER.info("The list of the trainee's training sessions was received with size {}",
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

    private Trainee getTraineeByUsername(String username) {
        return traineeDao.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Trainee was not found"));

    }
}
