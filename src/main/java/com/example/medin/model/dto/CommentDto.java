package com.example.medin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String doctorIin;
    private String pacientIin;
    private String doctorComment;
    private String pacientComment;
    private LocalDateTime date;
}
