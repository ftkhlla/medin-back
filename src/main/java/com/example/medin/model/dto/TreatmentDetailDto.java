package com.example.medin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDetailDto {
    private LocalDate date;
    private float percent;
    private List<ProgressDto> time;
    private List<MedicineDto> medicine;
}