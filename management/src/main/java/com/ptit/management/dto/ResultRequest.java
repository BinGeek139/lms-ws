package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResultRequest {
    String idQuestion;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    Integer correctAnswer;
}
