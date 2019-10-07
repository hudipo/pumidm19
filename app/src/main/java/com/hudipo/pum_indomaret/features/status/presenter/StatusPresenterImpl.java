package com.hudipo.pum_indomaret.features.status.presenter;

import android.util.Log;

import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.StatusFilterRequestBody;
import com.hudipo.pum_indomaret.features.status.model.StatusModel;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;

import java.util.List;

public class StatusPresenterImpl implements StatusContract.StatusPresenter, StatusContract.StatusInteractor.OnFinishedListenerStatus {

    private StatusContract.StatusView view;
    private StatusContract.StatusInteractor interactor;
    private int requestCode = 0;
    private StatusFilterRequestBody statusFilterRequestBody;

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
        if (requestCode==0){
            getStatusList();
        }else {
            getFilteredStatusList(statusFilterRequestBody);
        }
    }

    @Override
    public void getFilteredStatusList(StatusFilterRequestBody filterRequestBody) {
        statusFilterRequestBody = filterRequestBody;
        requestCode = 1;
        interactor.getFilteredStatusList(this, filterRequestBody);
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
