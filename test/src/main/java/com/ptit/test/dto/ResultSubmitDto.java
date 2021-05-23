package com.ptit.test.dto;

import java.util.Map;

public class ResultSubmitDto {
    private Integer quantity;
    private Integer quantityCorrect;
    private Map<String,Boolean> results;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityCorrect() {
        return quantityCorrect;
    }

    public void setQuantityCorrect(Integer quantityCorrect) {
        this.quantityCorrect = quantityCorrect;
    }

    public Map<String, Boolean> getResults() {
        return results;
    }

    public void setResults(Map<String, Boolean> results) {
        this.results = results;
    }
}
