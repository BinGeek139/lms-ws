package com.ptit.management.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuizRequest {
    @NotBlank(message = "Tên không được để trống")
    String name;
    @NotNull(message = "Số lượng không được để trống")
    Integer quantity;

    @NotNull(message = "Thời gian giới hạn không được để trống")
    Integer timeLimit;
    List<QuestionRequest> questions;
}
