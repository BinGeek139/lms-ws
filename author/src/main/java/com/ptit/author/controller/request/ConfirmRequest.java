package com.ptit.author.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ConfirmRequest {
    @NotNull(message = "id user không được để trống")
    @NotBlank(message = "id user không được để trống")
    String idUser;


    @NotBlank(message = "id user không được để trống")
    @NotNull(message = "code không được để trống")
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
