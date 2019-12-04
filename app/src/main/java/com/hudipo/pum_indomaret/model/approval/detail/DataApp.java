package com.hudipo.pum_indomaret.model.approval.detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataApp {

	@SerializedName("App_1")
	private List<AppItem> app1;

	@SerializedName("App_2")
	private List<AppItem> app2;

	@SerializedName("App_3")
	private List<AppItem> app3;

	@SerializedName("App_4")
	private List<AppItem> app4;

	@SerializedName("App_5")
	private List<AppItem> app5;

	public List<AppItem> getApp1() {
		return app1;
	}

	public List<AppItem> getApp2() {
		return app2;
	}

	public List<AppItem> getApp3() {
		return app3;
	}

	public List<AppItem> getApp4() {
		return app4;
	}

	public List<AppItem> getApp5() {
		return app5;
	}
}