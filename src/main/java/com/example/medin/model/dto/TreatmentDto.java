package com.example.medin.model.dto;

import com.example.medin.model.entity.Treatment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDto {
    private LocalDate start;
    private LocalDate end;
    private String frequencyInPeriod;
    private int frequencyInDay;
    private List<MedicineDto> medicine;
    private List<MedicineTimeDto> timeToMedicine;
    private String pacientIin;
    private String doctorIin;

    public TreatmentDto(Treatment treatment,
                        List<MedicineDto> medicineDtoList,
                        List<MedicineTimeDto> medicineTimeDtoList) {
        this.start = treatment.getStart();
        this.end = treatment.getEnd();
        this.frequencyInPeriod = treatment.getFrequencyInPeriod();
        this.frequencyInDay = treatment.getFrequencyInDay();
        this.medicine = medicineDtoList;
        this.timeToMedicine = medicineTimeDtoList;
    }
}
