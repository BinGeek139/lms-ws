package com.ptit.author.controller.response;

import lombok.Data;

@Data
public class ConfirmResponse {
    String token;

    public ConfirmResponse(String token) {
        this.token = token;
    }

    public ConfirmResponse() {
    }
}
