package com.example.medin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "medicine_times")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineTime {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "time_to_medicine")
    private String timeToMedicine;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    public MedicineTime(String timeToMedicine, Treatment treatment) {
        this.timeToMedicine = timeToMedicine;
        this.treatment = treatment;
    }
}
