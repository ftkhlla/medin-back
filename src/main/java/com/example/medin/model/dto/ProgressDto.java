package com.example.medin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressDto {
    private String time;
    private boolean done;
    private String doctorComment;
    private String pacientComment;
}
