package com.ptit.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnwserDto {
    private String id;
    private String code;
    private String name;
    private Integer status;
    private Integer isTrue;
}
