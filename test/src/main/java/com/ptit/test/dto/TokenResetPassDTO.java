package com.ptit.test.dto;

public class TokenResetPassDTO {

    private static final long serialVersionUID = 1L;
    private String username;
    private String token;
    private String newPass;
    private String oldPass;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getNewPass() {
        return newPass;
    }
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
    public String getOldPass() {
        return oldPass;
    }
    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }
}
