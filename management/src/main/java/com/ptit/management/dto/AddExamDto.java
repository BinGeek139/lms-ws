package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddExamDto {
    String id;
    List<AddExam> addExamList;
}
