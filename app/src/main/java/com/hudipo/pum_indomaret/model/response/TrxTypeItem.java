package com.hudipo.pum_indomaret.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TrxTypeItem implements Parcelable {

	@SerializedName("DESCRIPTION")
	private String dESCRIPTION;

	@SerializedName("CLEARING_ACCOUNT")
	private String cLEARINGACCOUNT;

	@SerializedName("PUM_RESP_TRX_TYPE_ID")
	private int pUMRESPTRXTYPEID;

	@SerializedName("NAME")
	private String nAME;

	private TrxTypeItem(Parcel in) {
		dESCRIPTION = in.readString();
		cLEARINGACCOUNT = in.readString();
		pUMRESPTRXTYPEID = in.readInt();
		nAME = in.readString();
	}

	public static final Creator<TrxTypeItem> CREATOR = new Creator<TrxTypeItem>() {
		@Override
		public TrxTypeItem createFromParcel(Parcel in) {
			return new TrxTypeItem(in);
		}

		@Override
		public TrxTypeItem[] newArray(int size) {
			return new TrxTypeItem[size];
		}
	};

	public void setDESCRIPTION(String dESCRIPTION){
		this.dESCRIPTION = dESCRIPTION;
	}

	public String getDESCRIPTION(){
		return dESCRIPTION;
	}

	public void setCLEARINGACCOUNT(String cLEARINGACCOUNT){
		this.cLEARINGACCOUNT = cLEARINGACCOUNT;
	}

	public String getCLEARINGACCOUNT(){
		return cLEARINGACCOUNT;
	}

	public void setPUMRESPTRXTYPEID(int pUMRESPTRXTYPEID){
		this.pUMRESPTRXTYPEID = pUMRESPTRXTYPEID;
	}

	public int getPUMRESPTRXTYPEID(){
		return pUMRESPTRXTYPEID;
	}

	public void setNAME(String nAME){
		this.nAME = nAME;
	}

	public String getNAME(){
		return nAME;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(dESCRIPTION);
		dest.writeString(cLEARINGACCOUNT);
		dest.writeInt(pUMRESPTRXTYPEID);
		dest.writeString(nAME);
	}
}