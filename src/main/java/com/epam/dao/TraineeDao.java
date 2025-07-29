package com.epam.dao;

import com.epam.model.Trainee;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TraineeDao {
    void save (Trainee trainee);
    Trainee findById(long id);
    List<Trainee> findAll();
    void update(Trainee trainee);
    void deletedById(long id);
}
