package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamDto {
    private String code;
    private String name;
    private String id;
    private String description;
    private Integer quantity;
    private Integer timeLimit;
    private Integer status;
}
