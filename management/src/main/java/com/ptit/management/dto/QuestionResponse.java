package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionResponse {
    private String id;
    private String code;
    private String name;
    private String note;
}
