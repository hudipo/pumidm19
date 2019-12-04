package com.hudipo.pum_indomaret.model.approval.detail;

import com.google.gson.annotations.SerializedName;

public class DataApproval {

	@SerializedName("PUM_TRX_TYPE_ID")
	private String pumTrxTypeId;

	@SerializedName("UPLOAD_DATA")
	private String uploadData;

	@SerializedName("USE_DATE")
	private String useDate;

	@SerializedName("EMP_NUM")
	private String empNum;

	@SerializedName("DEPARTMENT")
	private String department;

	@SerializedName("PUM_TRX_ID")
	private int pumTrxId;

	@SerializedName("RESP_ESTIMATE_DATE")
	private String respEstimateDate;

	@SerializedName("NAME")
	private String name;

	@SerializedName("DATA_APP")
	private DataApp dataApp;

	@SerializedName("EMP_ID")
	private int empId;

	@SerializedName("FILE_DATA")
	private String fileData;

	@SerializedName("TRX_DATE")
	private String trxDate;

	@SerializedName("DESCRIPTION")
	private String description;

	@SerializedName("AMOUNT")
	private int amount;

	@SerializedName("TRX_NUM")
	private String trxNum;

	public String getPumTrxTypeId() {
		return pumTrxTypeId;
	}

	public String getUploadData() {
		return uploadData;
	}

	public String getUseDate() {
		return useDate;
	}

	public String getEmpNum() {
		return empNum;
	}

	public String getDepartment() {
		return department;
	}

	public int getPumTrxId() {
		return pumTrxId;
	}

	public String getRespEstimateDate() {
		return respEstimateDate;
	}

	public String getName() {
		return name;
	}

	public DataApp getDataApp() {
		return dataApp;
	}

	public int getEmpId() {
		return empId;
	}

	public String getFileData() {
		return fileData;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public String getDescription() {
		return description;
	}

	public int getAmount() {
		return amount;
	}

	public String getTrxNum() {
		return trxNum;
	}
}