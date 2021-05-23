package com.ptit.author.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class RegisterRequest {
    @NotNull(message = "username không được để trống")
    private String userName;
    private String emailOrPhoneNumber;
    @NotNull(message = "password không được để trống")
    private String password;
    private String role;
}
