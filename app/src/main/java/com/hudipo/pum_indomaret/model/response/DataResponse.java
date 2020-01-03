package com.hudipo.pum_indomaret.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse implements Parcelable {

	@SerializedName("data")
	private List<DataResponseItem> data;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	private DataResponse(Parcel in) {
		error = in.readByte() != 0;
		message = in.readString();
	}

	public static final Creator<DataResponse> CREATOR = new Creator<DataResponse>() {
		@Override
		public DataResponse createFromParcel(Parcel in) {
			return new DataResponse(in);
		}

		@Override
		public DataResponse[] newArray(int size) {
			return new DataResponse[size];
		}
	};

	public void setData(List<DataResponseItem> data){
		this.data = data;
	}

	public List<DataResponseItem> getData(){
		return data;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (error ? 1 : 0));
		dest.writeString(message);
	}
}