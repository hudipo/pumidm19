package com.hudipo.pum_indomaret.features.report.contract;

import java.util.ArrayList;

public interface ReportContract {
    interface ReportView{
        void setDate(String date);
        void setViewData(ArrayList<String>reportTypeList,ArrayList<String> employeeNameList,ArrayList<String> departmentList, ArrayList<String> groupByList);
        void showDatePicker(int day, int month, int year);
    }

    interface ReportPresenter{
        void onDetach();
        void getViewData();
        void initDatePicker();
        void onDateSet(int day, int month, int year);
    }

    interface ReportInteractor{
        interface OnFinishedListener{
            void onDataFetched(ArrayList<String>reportTypeList,ArrayList<String> employeeNameList,ArrayList<String> departmentList, ArrayList<String> groupByList);
            void onFailure(Throwable throwable);
        }

        void getViewData(OnFinishedListener onFinishedListener);
    }

}
