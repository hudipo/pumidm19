package com.hudipo.pum_indomaret.features.requestpum.contract;

import com.hudipo.pum_indomaret.features.requestpum.model.DocumentDetailRequestModel;

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

        void onDetach();

    }

    interface SearchDocumentView{

        void setDocumentList(ArrayList<DocumentDetailRequestModel> documentDetailRequestModels);

    }

    interface SearchDocumentPresenter{

        void onDetach();

        void getDocumentList();

    }

    interface FundView{

        void setTransactionType(ArrayList<String>transactionTypeList);

    }

    interface FundPresenter{

        void onDetach();

        void getTransactionTypeList();
    }

    interface RequestInteractor {

        interface OnFinishedListenerEmployee {

            void onDepartmentFetched(ArrayList<String> departmentList);

            void onFailure(Throwable t);

            void onEmployeeNameFetched(String employeName);

        }

        interface OnFinishedListenerDocument{

            void onDocumentTypeFetched(ArrayList<String> documentTypeList);

            void onFailure(Throwable t);

        }

        interface OnFinishedListenerSearchDocument{

            void onDocumentDetailFetched(ArrayList<DocumentDetailRequestModel>documentDetailRequestModels);

            void onFailure(Throwable t);
        }

        interface OnFinishedListenerFund{

            void onTransactionTypeFetched(ArrayList<String>transactionTypeList);

            void onFailure(Throwable t);
        }

        void getEmployeeName(OnFinishedListenerEmployee onFinishedListenerEmployee);

        void getDepartmentList(OnFinishedListenerEmployee onFinishedListenerEmployee);

        void getDocumentTypeList(OnFinishedListenerDocument onFinishedListenerDocument);

        void getDocumentDetailList(OnFinishedListenerSearchDocument onFinishedListenerSearchDocument);

        void getTransactionTypeList(OnFinishedListenerFund onFinishedListenerFund);
    }
}
