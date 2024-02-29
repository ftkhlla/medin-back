package com.example.medin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "treatment_progress")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentProgress {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "pacient_comment")
    private String pacientComment;

    @Column(name = "doctor_comment")
    private String doctorComment;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    public TreatmentProgress(LocalDateTime time,
                             Treatment treatment) {
        this.time = time;
        this.treatment = treatment;
    }

    public TreatmentProgress(LocalDateTime time,
                             String pacientComment,
                             String doctorComment,
                             Treatment treatment) {
        this.time = time;
        this.pacientComment = pacientComment;
        this.doctorComment = doctorComment;
        this.treatment = treatment;
    }
}
