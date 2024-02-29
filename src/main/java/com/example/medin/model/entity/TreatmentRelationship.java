package com.example.medin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "treatment_relationship")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pacient_id")
    private User pacient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    public TreatmentRelationship(User pacient, User doctor) {
        this.pacient = pacient;
        this.doctor = doctor;
    }
}
