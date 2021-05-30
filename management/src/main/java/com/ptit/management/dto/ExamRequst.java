package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamRequst {
    private String name;
    private Integer quantity;
    private Integer timeLimit;
    private String classId;
    private List<String> question;
 }
