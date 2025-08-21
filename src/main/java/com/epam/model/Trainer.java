package com.epam.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author jdmon on 25/07/2025
 * @project springbasegymcrm
 */
@Entity
@Table(name = "trainers")
public class Trainer extends User{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "training_type_id", nullable = false)
    private TrainingType specialization;

    @ManyToMany (mappedBy = "trainers")
    private Set<Trainee> trainees = new LinkedHashSet<>();

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Training> trainings = new LinkedHashSet<>();

    public Trainer() {
        super();
    }

    public Trainer(String firstName, String lastName, String username,
                   String password, Boolean isActive, TrainingType specialization) {
        super(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    public Set<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(Set<Trainee> trainees) {
        this.trainees = trainees;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                super.toString() +
                specialization +
                '}';
    }
}
