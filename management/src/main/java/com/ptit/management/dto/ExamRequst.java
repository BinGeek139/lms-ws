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
public class ExamRequst {
    private String name;
    private Integer quantity;
    private Integer                                                                                                                                                                                  timeLimit;
    private String classId;
    private List<String> question;
 }
