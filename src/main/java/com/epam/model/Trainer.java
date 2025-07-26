package com.epam.model;

/**
 * @author jdmon on 25/07/2025
 * @project springbasegymcrm
 */
public class Trainer extends User{
    private TrainingType specialization;

    public Trainer() {
    }

    public Trainer(String userId, String firstName, String lastName, String userName, String password, boolean isActive, TrainingType specialization) {
        super(userId, firstName, lastName, userName, password, isActive);
        this.specialization = specialization;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }
}
