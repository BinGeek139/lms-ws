package com.ptit.author.controller.response;

public class RegisterResponse {
    String idUser;

    public RegisterResponse(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}