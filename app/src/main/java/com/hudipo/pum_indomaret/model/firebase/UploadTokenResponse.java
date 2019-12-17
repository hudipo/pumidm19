package com.hudipo.pum_indomaret.model.firebase;

import com.google.gson.annotations.SerializedName;

public class UploadTokenResponse {
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
