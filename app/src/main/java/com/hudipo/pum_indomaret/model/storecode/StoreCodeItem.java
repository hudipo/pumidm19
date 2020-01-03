package com.hudipo.pum_indomaret.model.storecode;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StoreCodeItem implements Parcelable {

	@SerializedName("STORE_CODE")
	private String sTORECODE;

	@SerializedName("STORE_NAME")
	private String sTORENAME;

	private StoreCodeItem(Parcel in) {
		sTORECODE = in.readString();
		sTORENAME = in.readString();
	}

	public static final Creator<StoreCodeItem> CREATOR = new Creator<StoreCodeItem>() {
		@Override
		public StoreCodeItem createFromParcel(Parcel in) {
			return new StoreCodeItem(in);
		}

		@Override
		public StoreCodeItem[] newArray(int size) {
			return new StoreCodeItem[size];
		}
	};

	public void setSTORECODE(String sTORECODE){
		this.sTORECODE = sTORECODE;
	}

	public String getSTORECODE(){
		return sTORECODE;
	}

	public void setSTORENAME(String sTORENAME){
		this.sTORENAME = sTORENAME;
	}

	public String getSTORENAME(){
		return sTORENAME;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(sTORECODE);
		dest.writeString(sTORENAME);
	}
}