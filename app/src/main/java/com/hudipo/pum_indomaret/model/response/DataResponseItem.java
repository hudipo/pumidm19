package com.hudipo.pum_indomaret.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataResponseItem implements Parcelable {

	@SerializedName("PUM_TRX_TYPE_ID")
	private int pUMTRXTYPEID;

	@SerializedName("TRX_TYPE")
	private List<TrxTypeItem> tRXTYPE;

	@SerializedName("PO_NUMBER")
	private String pONUMBER;

	@SerializedName("EMP_ID")
	private int eMPID;

	@SerializedName("AMOUNT")
	private int aMOUNT;

	@SerializedName("DESCRIPTION")
	private String dESCRIPTION;

	@SerializedName("AMOUNT_REMAINING")
	private int aMOUNTREMAINING;

	@SerializedName("RESP_STATUS")
	private String rESPSTATUS;

	@SerializedName("TRX_TYPE_DESCRIPTION")
	private String tRXTYPEDESCRIPTION;

	@SerializedName("TRX_NUM")
	private String tRXNUM;

	@SerializedName("PUM_TRX_ID")
	private int pUMTRXID;



	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(pUMTRXTYPEID);
		dest.writeString(pONUMBER);
		dest.writeInt(eMPID);
		dest.writeInt(aMOUNT);
		dest.writeString(dESCRIPTION);
		dest.writeInt(aMOUNTREMAINING);
		dest.writeString(rESPSTATUS);
		dest.writeString(tRXTYPEDESCRIPTION);
		dest.writeString(tRXNUM);
		dest.writeInt(pUMTRXID);
		dest.writeList(tRXTYPE);
	}

	private DataResponseItem(Parcel in) {
		pUMTRXTYPEID = in.readInt();
		pONUMBER = in.readString();
		eMPID = in.readInt();
		aMOUNT = in.readInt();
		dESCRIPTION = in.readString();
		aMOUNTREMAINING = in.readInt();
		rESPSTATUS = in.readString();
		tRXTYPEDESCRIPTION = in.readString();
		tRXNUM = in.readString();
		pUMTRXID = in.readInt();
		tRXTYPE = new ArrayList<>();
		in.readList(tRXTYPE, TrxTypeItem.class.getClassLoader());
	}

	public static final Creator<DataResponseItem> CREATOR = new Creator<DataResponseItem>() {
		@Override
		public DataResponseItem createFromParcel(Parcel in) {
			return new DataResponseItem(in);
		}

		@Override
		public DataResponseItem[] newArray(int size) {
			return new DataResponseItem[size];
		}
	};

	public void setPUMTRXTYPEID(int pUMTRXTYPEID){
		this.pUMTRXTYPEID = pUMTRXTYPEID;
	}

	public int getPUMTRXTYPEID(){
		return pUMTRXTYPEID;
	}

	public void setTRXTYPE(List<TrxTypeItem> tRXTYPE){
		this.tRXTYPE = tRXTYPE;
	}

	public List<TrxTypeItem> getTRXTYPE(){
		return tRXTYPE;
	}

	public void setPONUMBER(String pONUMBER){
		this.pONUMBER = pONUMBER;
	}

	public String getPONUMBER(){
		return pONUMBER;
	}

	public void setEMPID(int eMPID){
		this.eMPID = eMPID;
	}

	public int getEMPID(){
		return eMPID;
	}

	public void setAMOUNT(int aMOUNT){
		this.aMOUNT = aMOUNT;
	}

	public int getAMOUNT(){
		return aMOUNT;
	}

	public void setDESCRIPTION(String dESCRIPTION){
		this.dESCRIPTION = dESCRIPTION;
	}

	public String getDESCRIPTION(){
		return dESCRIPTION;
	}

	public void setAMOUNTREMAINING(int aMOUNTREMAINING){
		this.aMOUNTREMAINING = aMOUNTREMAINING;
	}

	public int getAMOUNTREMAINING(){
		return aMOUNTREMAINING;
	}

	public void setRESPSTATUS(String rESPSTATUS){
		this.rESPSTATUS = rESPSTATUS;
	}

	public String getRESPSTATUS(){
		return rESPSTATUS;
	}

	public void setTRXTYPEDESCRIPTION(String tRXTYPEDESCRIPTION){
		this.tRXTYPEDESCRIPTION = tRXTYPEDESCRIPTION;
	}

	public String getTRXTYPEDESCRIPTION(){
		return tRXTYPEDESCRIPTION;
	}

	public void setTRXNUM(String tRXNUM){
		this.tRXNUM = tRXNUM;
	}

	public String getTRXNUM(){
		return tRXNUM;
	}

	public void setPUMTRXID(int pUMTRXID){
		this.pUMTRXID = pUMTRXID;
	}

	public int getPUMTRXID(){
		return pUMTRXID;
	}

	@Override
	public int describeContents() {
		return 0;
	}
}