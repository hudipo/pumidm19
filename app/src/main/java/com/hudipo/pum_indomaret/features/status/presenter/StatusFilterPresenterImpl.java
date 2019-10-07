package com.hudipo.pum_indomaret.features.status.presenter;

import com.hudipo.pum_indomaret.features.status.StatusFilterActivity;
import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.StatusFilterRequestBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatusFilterPresenterImpl implements StatusContract.StatusFilterPresenter,StatusContract.StatusFilterInteractor.OnFinishedListenerStatusFilter {

    private StatusContract.StatusFilterView view;
    private StatusContract.StatusFilterInteractor interactor;
    private int requestCode = 0;
    private StatusFilterRequestBody statusFilterRequestBody;

    public StatusFilterPresenterImpl(StatusContract.StatusFilterView view, StatusContract.StatusFilterInteractor interactor){
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        month = month + 1;
        String date = year + "-" + month + "-" + day;

        if (requestCode==StatusFilterActivity.START_DATE_CODE){
            view.setStartDate(date);
        }else view.setUntilDate(date);
    }

    @Override
    public void getDatePickerData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        view.setDatePickerView(year,month,day);
    }

    @Override
    public void onStartDateClicked() {
        requestCode = StatusFilterActivity.START_DATE_CODE;
        getDatePickerData();
    }

    @Override
    public void onUntilDateClicked() {
        requestCode = StatusFilterActivity.UNTIL_DATE_CODE;
        getDatePickerData();
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getStatusList() {
        interactor.getStatusList(this);
    }


    @Override
    public void onStatusListFetched(ArrayList<String> statusList) {
        view.setStatusList(statusList);
    }

    @Override
    public void onFailure(String errorMessage) {

    }
}
