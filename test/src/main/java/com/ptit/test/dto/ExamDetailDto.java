package com.ptit.test.dto;

import lombok.Data;

@Data
public class ExamDetailDto {
    private String code;
    private String name;
    private String id;
    private String description;
    private Integer quantity;
    private Integer timeLimit;
    private Integer status;
}
