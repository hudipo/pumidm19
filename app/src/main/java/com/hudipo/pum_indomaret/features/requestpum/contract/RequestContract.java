package com.hudipo.pum_indomaret.features.requestpum.contract;

import java.util.ArrayList;

public interface RequestContract {

    interface EmployeeView {

        void setEmployeeName(String employeeName);

        void setDepartmentList(ArrayList<String> departmentList);

        void displayDatePickerDialog(int year, int month, int day,int dateCode);

        void setDate (String date, int dateCode);

    }

    interface EmployeePresenter {

        void onDestroy();

        void formValidation();

        void getDepartmentList();

        void getEmployeeName();

        void onDateSet(int year, int month, int day,int dateCode);

        void onDateClicked(int dateCode);

    }

    interface DocumentView{
        void setDocumentType(ArrayList<String> documentType);
    }

    interface DocumentPresenter{
        void getDocumentType();
        void onDestroy();
    }

    interface RequestIntractor {

        interface OnFinishedListener {

            void onDepartmentFetched(ArrayList<String> departmentList);

            void onFailure(Throwable t);

            void onEmployeeNameFetched(String employeName);

            void onDocumentTypeFetched(ArrayList<String> documentTypeList);
        }

        void getEmployeeName(OnFinishedListener onFinishedListener);

        void getDepartmentList(OnFinishedListener onFinishedListener);

        void getDocumentTypeList(OnFinishedListener onFinishedListener);
    }
}
