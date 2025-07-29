package com.epam.service.impl;

import com.epam.dao.TraineeDao;
import com.epam.model.Trainee;
import com.epam.service.TraineeService;
import com.epam.util.UserUtil;
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

    public TraineeServiceImpl(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setUserUtil(UserUtil userUtil){
        this.userUtil=userUtil;
    }

    @Override
    public void createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        Trainee traineeToCreate = new Trainee();
        traineeToCreate.setFirstName(firstName);
        traineeToCreate.setLastName(lastName);
        traineeToCreate.setUserName(userUtil.generateUsername(firstName,lastName,userUtil.getUserNames()));
        traineeToCreate.setPassword(userUtil.generateRandomPassword());
        traineeToCreate.setActive(true);
        traineeToCreate.setDateOfBirth(dateOfBirth);
        traineeToCreate.setAddress(address);
        traineeDao.save(traineeToCreate);
        userUtil.addUsernames(traineeToCreate.getUserName());
    }

    @Override
    public void updateTrainee(long userId, LocalDate dateOfBirth, String address) {
        Trainee traineeToUpdated = selectTrainee(userId);
        traineeToUpdated.setDateOfBirth(dateOfBirth);
        traineeToUpdated.setAddress(address);
        traineeDao.update(traineeToUpdated);
    }

    @Override
    public void deleteTrainee(long id) {
        traineeDao.deletedById(id);
    }

    @Override
    public Trainee selectTrainee(long id) {
        return traineeDao.findById(id);
    }

    @Override
    public List<Trainee> selectAllTrainees() {
        return traineeDao.findAll();
    }
}
