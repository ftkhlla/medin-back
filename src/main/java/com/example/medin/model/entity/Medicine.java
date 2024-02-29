package com.example.medin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "medicinies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    public Medicine(String name, Treatment treatment) {
        this.name = name;
        this.treatment = treatment;
    }
}
