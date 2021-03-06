package com.ptit.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClazzDto {
    private String id;
    private String name;
    private String code;
    private String description;
    private Integer status;
}
