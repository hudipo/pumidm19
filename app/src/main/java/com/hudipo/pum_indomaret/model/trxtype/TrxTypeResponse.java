package com.hudipo.pum_indomaret.model.trxtype;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrxTypeResponse implements Parcelable {

	@SerializedName("data")
	private List<TrxItem> trx;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	private TrxTypeResponse(Parcel in) {
		trx = in.createTypedArrayList(TrxItem.CREATOR);
		error = in.readByte() != 0;
		message = in.readString();
	}

	public static final Creator<TrxTypeResponse> CREATOR = new Creator<TrxTypeResponse>() {
		@Override
		public TrxTypeResponse createFromParcel(Parcel in) {
			return new TrxTypeResponse(in);
		}

		@Override
		public TrxTypeResponse[] newArray(int size) {
			return new TrxTypeResponse[size];
		}
	};

	public List<TrxItem> getTrx() {
		return trx;
	}

	public void setTrx(List<TrxItem> trx) {
		this.trx = trx;
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
		dest.writeTypedList(trx);
		dest.writeByte((byte) (error ? 1 : 0));
		dest.writeString(message);
	}
}