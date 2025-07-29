package com.epam.dao;

import com.epam.model.Trainer;

import java.util.List;

/**
 * @author jdmon on 26/07/2025
 * @project springbasegymcrm
 */
public interface TrainerDao {
    void save (Trainer trainer);
    Trainer findById(long id);
    List<Trainer> findAll();
    void update(Trainer trainer);
}
