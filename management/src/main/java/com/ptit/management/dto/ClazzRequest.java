package com.ptit.management.dto;

import com.ptit.management.entity.Exam;
import com.ptit.management.entity.UserClazz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClazzRequest {
    private String name;
    private String description;
}
