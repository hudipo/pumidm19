package com.hudipo.pum_indomaret.features.status.presenter;

import android.util.Log;

import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.FilterStatusModel;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;

import java.util.List;

public class StatusPresenterImpl implements StatusContract.StatusPresenter, StatusContract.StatusInteractor.OnFinishedListenerStatus {

    private StatusContract.StatusView view;
    private StatusContract.StatusInteractor interactor;

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
        Log.d("presenter","getStatList");
        view.showLoading();
        interactor.getStatusList(this);
    }

    @Override
    public void getFilteredStatusList(FilterStatusModel filterStatusModel) {
        interactor.getFilteredStatusList(this, filterStatusModel);
    }

    @Override
    public void onStatusListFetched(List<StatusResponse.StatusModel> statusList) {
        view.hideLoading();
        view.setStatusList(statusList);
        Log.d("presenter","called");
    }

    @Override
    public void onFailure(Throwable t) {
        view.hideLoading();
        view.toast(t.getMessage());
    }
}
