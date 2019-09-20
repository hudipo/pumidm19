package com.hudipo.pum_indomaret.features.report.presenter;

import com.hudipo.pum_indomaret.features.report.contract.ReportContract;

import java.util.ArrayList;
import java.util.Calendar;

public class ReportPresenterImpl implements ReportContract.ReportPresenter,ReportContract.ReportInteractor.OnFinishedListener {

    private ReportContract.ReportInteractor interactor;
    private ReportContract.ReportView reportView;

    public ReportPresenterImpl(ReportContract.ReportView reportView, ReportContract.ReportInteractor interactor){
        this.reportView = reportView;
        this.interactor = interactor;
    }
    @Override
    public void onDetach() {
        reportView = null;
        interactor = null;
    }

    @Override
    public void getViewData() {
        interactor.getViewData(this);
    }

    @Override
    public void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        reportView.showDatePicker(day, month, year);
    }

    @Override
    public void onDateSet(int day, int month, int year) {
        month = month + 1;
        String date = day + "/" + month + "/" + year;
        reportView.setDate(date);
    }

    @Override
    public void onDataFetched(ArrayList<String> reportTypeList, ArrayList<String> employeeNameList, ArrayList<String> departmentList, ArrayList<String> groupByList) {
        reportView.setViewData(reportTypeList,employeeNameList,departmentList,groupByList);
    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
