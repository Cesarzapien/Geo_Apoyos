package com.cesar.geoapoyos3.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
