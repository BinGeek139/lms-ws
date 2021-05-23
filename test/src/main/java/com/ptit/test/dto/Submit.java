package com.ptit.test.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Submit {
    @NotNull(message = "Mã bài thi không được để trống")
    String resultId;
    List<String> idAnswer;

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public List<String> getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(List<String> idAnswer) {
        this.idAnswer = idAnswer;
    }
}
