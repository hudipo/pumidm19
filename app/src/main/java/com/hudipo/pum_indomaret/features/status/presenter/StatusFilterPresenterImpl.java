package com.hudipo.pum_indomaret.features.status.presenter;


import com.hudipo.pum_indomaret.features.status.StatusFilterActivity;
import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.StatusFilterRequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StatusFilterPresenterImpl implements StatusContract.StatusFilterPresenter,StatusContract.StatusFilterInteractor.OnFinishedListenerStatusFilter {

    private StatusContract.StatusFilterView view;
    private StatusContract.StatusFilterInteractor interactor;
    private int requestCode = 0;
    //private StatusFilterRequestBody statusFilterRequestBody;

    public StatusFilterPresenterImpl(StatusContract.StatusFilterView view, StatusContract.StatusFilterInteractor interactor){
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void validateDate(String startDate, String untilDate, String status) {
        if (startDate.equals("--")){
            view.toast("Please Select Start Date!");
        }else if (untilDate.equals("--")){
            view.toast("Please Select Until Date");
        }else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date startDateFormat = sdf.parse(startDate);
                Date untilDateFormat = sdf.parse(untilDate);
                long todayInMilis = Calendar.getInstance().getTimeInMillis();
                long startDateDiffMil = todayInMilis - startDateFormat.getTime();
                long untilDateDiffMil = todayInMilis - untilDateFormat.getTime();
                long startDateDiffDay = TimeUnit.MILLISECONDS.toDays(startDateDiffMil);
                long untilDateDiffDay = TimeUnit.MILLISECONDS.toDays(untilDateDiffMil);
                if (startDateDiffDay>31 || untilDateDiffDay>31){
                    view.toast("Please Input a Valid Date!");
                }else{
                    view.goBackToStatusAct(startDate,untilDate,status);
                }

            } catch (ParseException e) {
                view.toast(e.getMessage());
            }
        }
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
