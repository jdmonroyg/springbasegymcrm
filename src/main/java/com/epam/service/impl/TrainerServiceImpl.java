package com.epam.service.impl;

import com.epam.dao.TrainerDao;
import com.epam.model.Trainer;
import com.epam.model.TrainingType;
import com.epam.service.TrainerService;
import com.epam.util.UserUtil;
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

    public TrainerServiceImpl(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setUserUtil(UserUtil userUtil){
        this.userUtil=userUtil;
    }

    @Override
    public void createTrainer(String firstName, String lastName, String specialization) {
        Trainer trainerToCreate = new Trainer();
        trainerToCreate.setFirstName(firstName);
        trainerToCreate.setLastName(lastName);
        trainerToCreate.setUserName(userUtil.generateUsername(firstName,lastName,userUtil.getUserNames()));
        trainerToCreate.setPassword(userUtil.generateRandomPassword());
        trainerToCreate.setActive(true);
        trainerToCreate.setSpecialization(TrainingType.valueOf(specialization));
        trainerDao.save(trainerToCreate);
        userUtil.addUsernames(trainerToCreate.getUserName());
    }

    @Override
    public void updateTrainer(long userId, String specialization) {
        Trainer trainerToUpdated = selectTrainer(userId);
        trainerToUpdated.setSpecialization(TrainingType.valueOf(specialization));
        trainerDao.update(trainerToUpdated);
    }

    @Override
    public Trainer selectTrainer(long id) {
        return trainerDao.findById(id);
    }

    @Override
    public List<Trainer> selectAllTrainers() {
        return trainerDao.findAll();
    }
}
