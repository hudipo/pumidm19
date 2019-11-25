package com.hudipo.pum_indomaret.model.docdetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DocDetailResponse implements Parcelable {

	@SerializedName("data")
	private DocData docData;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	private DocDetailResponse(Parcel in) {
		docData = in.readParcelable(DocData.class.getClassLoader());
		error = in.readByte() != 0;
		message = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(docData, flags);
		dest.writeByte((byte) (error ? 1 : 0));
		dest.writeString(message);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<DocDetailResponse> CREATOR = new Creator<DocDetailResponse>() {
		@Override
		public DocDetailResponse createFromParcel(Parcel in) {
			return new DocDetailResponse(in);
		}

		@Override
		public DocDetailResponse[] newArray(int size) {
			return new DocDetailResponse[size];
		}
	};

	public void setDocData(DocData docData){
		this.docData = docData;
	}

	public DocData getDocData(){
		return docData;
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
}