package com.hudipo.pum_indomaret.model.logout;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public boolean isError(){
        return error;
    }

    public String getMessage(){
        return message;
    }
}
