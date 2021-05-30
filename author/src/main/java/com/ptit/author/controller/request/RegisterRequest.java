package com.ptit.author.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterRequest {
    @NotNull(message = "username không được để trống")
    private String userName;
    @NotNull(message = "Số điện thoại hoặc email không được để trống")
    private String emailOrPhoneNumber;
    @NotNull(message ="Tên không được để trống")
    private String name;
    @NotNull(message = "password không được để trống")
    private String password;
    @Pattern(regexp = "0|1",message = "Role không hợp lệ")
    private String role;
}
