package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionRequest {
    String nameQuestion;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    Integer correctAnswer;


}
