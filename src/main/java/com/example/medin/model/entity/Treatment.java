package com.example.medin.model.entity;

import com.example.medin.model.dto.TreatmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "treatments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Treatment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "treatment_start")
    private LocalDate start;

    @Column(name = "treatment_end")
    private LocalDate end;

    @Column(name = "frequency_in_period")
    private String frequencyInPeriod;

    @Column(name = "frequency_in_day")
    private int frequencyInDay;

    @ManyToOne
    @JoinColumn(name = "pacient_id")
    private User pacient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    public Treatment(TreatmentDto dto,
                     User pacient,
                     User doctor) {
        this.start = dto.getStart();
        this.end = dto.getEnd();
        this.frequencyInPeriod = dto.getFrequencyInPeriod();
        this.frequencyInDay = dto.getFrequencyInDay();
        this.pacient = pacient;
        this.doctor = doctor;
    }
}
