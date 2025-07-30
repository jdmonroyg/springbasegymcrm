package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import com.epam.model.User;
import com.epam.service.TraineeService;
import com.epam.util.UserUtil;
import com.epam.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDao traineeDao;
    private UserUtil userUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    public TraineeServiceImpl(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setUserUtil(UserUtil userUtil){
        this.userUtil=userUtil;
    }

    @Override
    public void createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        ValidationUtil.requireNonBlank(firstName, "firstName");
        ValidationUtil.requireNonBlank(lastName, "lastName");
        ValidationUtil.requireNonNull(dateOfBirth, "dateOfBirth");
        ValidationUtil.requireNonBlank(address, "address");

        Trainee traineeToCreate = new Trainee();
        traineeToCreate.setUserId(User.generateNextId());
        traineeToCreate.setFirstName(firstName);
        traineeToCreate.setLastName(lastName);
        traineeToCreate.setUserName(userUtil.generateUsername(firstName,lastName,userUtil.getUserNames()));
        traineeToCreate.setPassword(userUtil.generateRandomPassword());
        traineeToCreate.setActive(true);
        traineeToCreate.setDateOfBirth(dateOfBirth);
        traineeToCreate.setAddress(address);
        traineeDao.save(traineeToCreate);
        userUtil.addUsernames(traineeToCreate.getUserName());
        LOGGER.debug("The {} was saved", traineeToCreate);
    }

    @Override
    public void updateTrainee(long userId, LocalDate dateOfBirth, String address) {
        ValidationUtil.requireNonNull(dateOfBirth, "dateOfBirth");
        ValidationUtil.requireNonBlank(address, "address");

        Trainee traineeToUpdated = selectTrainee(userId);
        traineeToUpdated.setDateOfBirth(dateOfBirth);
        traineeToUpdated.setAddress(address);
        traineeDao.update(traineeToUpdated);
        LOGGER.debug("The {} was updated", traineeToUpdated);
    }

    @Override
    public void deleteTrainee(long id) {
        Trainee traineeToDeleted = selectTrainee(id);
        traineeDao.deletedById(id);
        LOGGER.debug("The {} was deleted", traineeToDeleted);
    }

    @Override
    public Trainee selectTrainee(long id) {
        Trainee traineeSelected = traineeDao.findById(id);
        ValidationUtil.requireExisting(traineeSelected, "Trainee with id: "+id);
        LOGGER.debug("The {} was select", traineeSelected);
        return traineeSelected;
    }

    @Override
    public List<Trainee> selectAllTrainees() {
        return traineeDao.findAll();
    }
}
