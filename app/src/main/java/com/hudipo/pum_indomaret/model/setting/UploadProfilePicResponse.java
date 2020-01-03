package com.hudipo.pum_indomaret.model.setting;

import com.google.gson.annotations.SerializedName;

public class UploadProfilePicResponse{

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
	private String data;

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}

	public String getData() {
		return data;
	}
}