package com.cesar.geoapoyos3.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("correo")
    String correo;
    @SerializedName("password")
    String password;

    public LoginRequest(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public LoginRequest() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
