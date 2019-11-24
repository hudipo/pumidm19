package com.hudipo.pum_indomaret.model.trxtype;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TrxItem implements Parcelable {

	@SerializedName("PUM_TRX_TYPE_ID")
	private int pumTrxTypeId;

	@SerializedName("DESCRIPTION")
	private String description;

	public int getPumTrxTypeId() {
		return pumTrxTypeId;
	}

	public void setPumTrxTypeId(int pumTrxTypeId) {
		this.pumTrxTypeId = pumTrxTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private TrxItem(Parcel in) {
		pumTrxTypeId = in.readInt();
		description = in.readString();
	}

	public static final Creator<TrxItem> CREATOR = new Creator<TrxItem>() {
		@Override
		public TrxItem createFromParcel(Parcel in) {
			return new TrxItem(in);
		}

		@Override
		public TrxItem[] newArray(int size) {
			return new TrxItem[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(pumTrxTypeId);
		dest.writeString(description);
	}
}