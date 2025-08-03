package com.epam.model;

import java.time.LocalDate;

/**
 * @author jdmon on 25/07/2025
 * @project springbasegymcrm
 */
public class Training {
    private long trainingId;
    private long traineeId;
    private long trainerId;
    private String trainingName;
    private TrainingType type;
    private LocalDate trainingDate;
    private int durationInMinutes;

    private static long nextId;

    public Training() {
    }

    public Training(long trainingId, long traineeId, long trainerId, String trainingName, TrainingType type,
                    LocalDate trainingDate, int durationInMinutes) {
        this.trainingId = trainingId;
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.trainingName = trainingName;
        this.type = type;
        this.trainingDate = trainingDate;
        this.durationInMinutes = durationInMinutes;
    }

    public static long generateNextId() {
        return ++nextId;
    }

    public static void initializeNextId(long maxExistingId) {
        if (maxExistingId > nextId) {
            nextId = maxExistingId;
        }
    }

    public static void setNextId(long nextId) {
        Training.nextId = nextId;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(long traineeId) {
        this.traineeId = traineeId;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
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

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public String toString() {
        return "Training{" +
                "trainingId=" + trainingId +
                ", traineeId=" + traineeId +
                ", trainerId=" + trainerId +
                ", trainingName='" + trainingName + '\'' +
                ", type=" + type +
                ", trainingDate=" + trainingDate +
                ", durationInMinutes=" + durationInMinutes +
                '}';
    }
}
