package com.ptit.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamResponse {
    private String code;
    private String name;
    private String id;
    private String description;
    private Integer quantity;
    private Integer timeLimit;
}
