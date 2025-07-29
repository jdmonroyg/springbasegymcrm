package com.epam.dao;

import com.epam.model.Training;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TrainingDao {
    void save (Training training);
    Training findById(long id);
    List<Training> findAll();
}
