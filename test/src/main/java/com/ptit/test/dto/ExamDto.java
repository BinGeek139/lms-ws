package com.ptit.test.dto;

import java.util.List;

public class ExamDto {
    private String code;
    private String name;
    private String id;
    private String description;
    private Integer quantity;
    private Integer timeLimit;
    private List<QuestionDto> questionDtoList;
    private String idResult;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public List<QuestionDto> getQuestionDtoList() {
        return questionDtoList;
    }

    public void setQuestionDtoList(List<QuestionDto> questionDtoList) {
        this.questionDtoList = questionDtoList;
    }

    public String getIdResult() {
        return idResult;
    }

    public void setIdResult(String idResult) {
        this.idResult = idResult;
    }
}
