package com.epam.service.impl;

import com.epam.dao.TrainerDao;
import com.epam.model.Trainer;
import com.epam.model.TrainingType;
import com.epam.model.User;
import com.epam.service.TrainerService;
import com.epam.util.UserUtil;
import com.epam.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDao trainerDao;
    private UserUtil userUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    public TrainerServiceImpl(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setUserUtil(UserUtil userUtil){
        this.userUtil=userUtil;
    }

    @Override
    public void createTrainer(String firstName, String lastName, String specialization) {
        ValidationUtil.requireNonBlank(firstName, "firstName");
        ValidationUtil.requireNonBlank(lastName, "lastName");
        ValidationUtil.requireNonNull(specialization, "specialization");

        Trainer trainerToCreate = new Trainer();
        trainerToCreate.setUserId(User.generateNextId());
        trainerToCreate.setFirstName(firstName);
        trainerToCreate.setLastName(lastName);
        trainerToCreate.setUserName(userUtil.generateUsername(firstName,lastName,userUtil.getUserNames()));
        trainerToCreate.setPassword(userUtil.generateRandomPassword());
        trainerToCreate.setActive(true);
        trainerToCreate.setSpecialization(TrainingType.valueOf(specialization.toUpperCase()));
        trainerDao.save(trainerToCreate);
        userUtil.addUsernames(trainerToCreate.getUserName());
        LOGGER.debug("The {} was saved", trainerToCreate);
    }

    @Override
    public void updateTrainer(long userId, String specialization) {
        ValidationUtil.requireNonBlank(specialization, "specialization");

        Trainer trainerToUpdated = selectTrainer(userId);
        trainerToUpdated.setSpecialization(TrainingType.valueOf(specialization));
        trainerDao.update(trainerToUpdated);
        LOGGER.debug("The {} was updated", trainerToUpdated);
    }

    @Override
    public Trainer selectTrainer(long id) {
        Trainer trainerSelected = trainerDao.findById(id);
        ValidationUtil.requireExisting(trainerSelected, "Trainer with id: "+id);
        LOGGER.debug("The {} was select", trainerSelected);
        return trainerSelected;
    }

    @Override
    public List<Trainer> selectAllTrainers() {
        return trainerDao.findAll();
    }
}
