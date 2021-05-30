package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClazzResponse {
    private String id;
    private String name;
    private String code;
    private String description;
    private Integer status;
}
