package com.hudipo.pum_indomaret.features.status.presenter;

import com.hudipo.pum_indomaret.features.status.StatusActivity;
import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.StatusFilterRequestBody;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StatusPresenterImpl implements StatusContract.StatusPresenter, StatusContract.StatusInteractor.OnFinishedListenerStatus {

    private StatusContract.StatusView view;
    private StatusContract.StatusInteractor interactor;
    private int requestCode = 0;
    private String startDate,untilDate,status;

    public StatusPresenterImpl(StatusContract.StatusView view, StatusContract.StatusInteractor interactor){
        this.view = view;
        this.interactor = interactor;
    }


    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getStatusList() {
        requestCode = 0;
        view.showLoading();
        interactor.getStatusList(this);
    }

    @Override
    public void onRefresh() {
        if (requestCode == StatusActivity.STATUS_REQUEST_CODE){
            getStatusList();
        }else {
            getFilteredStatusList(startDate,untilDate,status);
        }
    }

    @Override
    public void getFilteredStatusList(String startDate, String untilDate, String status) {
        this.startDate = startDate;
        this.untilDate = untilDate;
        this.status = status;
        requestCode = StatusActivity.STATUS_FILTER_REQUEST_CODE;
        view.showLoading();
        if (!status.equals("--")){
            if (status.equals("New")){
                status = "N";
            }else if (status.equals("Process")){
                status = "APP";
            }else if (status.equals("Reject")){
                status = "R";
            }else{
                status = "I";
            }
        }
        interactor.getFilteredStatusList(this,status,startDate,untilDate);
    }

    @Override
    public void onStatusListFetched(List<StatusResponse.StatusModel> statusList) {
        view.hideLoading();
        view.setStatusList(statusList);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.hideLoading();
        view.toast(errorMessage);
    }

}
