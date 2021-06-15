package com.ptit.management.dto;

import com.ptit.management.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionDto {
    private String id;
    private String code;
    private String name;
    private String note;
    private int status;
    private String urlImage;
    private List<AnwserDto> answers;
}
