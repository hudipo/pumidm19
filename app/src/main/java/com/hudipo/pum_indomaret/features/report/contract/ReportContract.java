package com.hudipo.pum_indomaret.features.report.contract;


import android.net.Uri;

public interface ReportContract {
    interface ReportView{
        void setDate(String date);
        void showDatePicker(int day, int month, int year);
        void toast(String message);
        void showProgress();
        void hideProgress();
        void showPdf(Uri uri);
        void askToDownload();
    }

    interface ReportPresenter{
        void onDetach();
        void initDatePicker();
        void onDateSet(int day, int month, int year);
        void checkData(int reportType,String startDate, String endDate, String validStartDate, String validEndDate, String pumStatus, String respStatus, String groupBy);
        void downloadData();
    }

}
