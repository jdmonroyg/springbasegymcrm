package com.epam.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author jdmon on 25/07/2025
 * @project springbasegymcrm
 */
@Entity
@Table(name = "training_types")
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_type_id")
    private Long id;

    @Column(name = "training_type_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "specialization")
    private Set<Trainer> trainers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "trainingType")
    private Set<Training> trainings = new LinkedHashSet<>();

    public TrainingType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public String toString() {
        return  ", trainingTypeId=" + id +
                ", name='" + name + '\''
                ;
    }
}
