package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ExamResponse {
    private String code;
    private String name;
    private String id;
    private String description;
    private Integer quantity;
    private Integer timeLimit;
    private Integer status;
    List<QuestionResponse> questions;
}
