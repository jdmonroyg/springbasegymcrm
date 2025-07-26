package com.epam.model;

import java.time.LocalDate;

/**
 * @author jdmon on 25/07/2025
 * @project springbasegymcrm
 */
public class Training {
    private long trainingId;
    private Trainee traineeId;
    private Trainer trainerId;
    private String trainingName;
    private TrainingType type;
    private LocalDate trainingDate;
    private int trainingDuration;

    public Training() {
    }

    public Training(long trainingId, Trainee traineeId, Trainer trainerId, String trainingName, TrainingType type,
                    LocalDate trainingDate, int trainingDuration) {
        this.trainingId = trainingId;
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.trainingName = trainingName;
        this.type = type;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    public Trainee getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Trainee traineeId) {
        this.traineeId = traineeId;
    }

    public Trainer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Trainer trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public TrainingType getType() {
        return type;
    }

    public void setType(TrainingType type) {
        this.type = type;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public int getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(int trainingDuration) {
        this.trainingDuration = trainingDuration;
    }
}
