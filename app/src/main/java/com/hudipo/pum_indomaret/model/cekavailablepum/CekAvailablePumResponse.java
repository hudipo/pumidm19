package com.hudipo.pum_indomaret.model.cekavailablepum;

import com.google.gson.annotations.SerializedName;

public class CekAvailablePumResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
