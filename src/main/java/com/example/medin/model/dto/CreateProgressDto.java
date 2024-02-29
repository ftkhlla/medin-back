package com.example.medin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProgressDto {
    private String doctorIin;
    private String pacientIin;
    private List<ProgressDto> progress;

}
