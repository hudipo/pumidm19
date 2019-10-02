package com.hudipo.pum_indomaret.features.requestpum.model;

import android.content.Context;

import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.ArrayList;

public class RequestInteractorImpl implements RequestContract.RequestInteractor {

    private HawkStorage hawkStorage;

    public RequestInteractorImpl(Context context) {
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void getEmployeeName(OnFinishedListenerEmployee onFinishedListenerEmployee) {
        onFinishedListenerEmployee.onEmployeeNameFetched(hawkStorage.getUserData().getName());
    }

    @Override
    public void getDepartmentList(OnFinishedListenerEmployee onFinishedListenerEmployee) {
        ArrayList<String>departmentList = new ArrayList<>();
        for (int i=0;i<10;i++){
            departmentList.add("Department "+i);
        }
        onFinishedListenerEmployee.onDepartmentFetched(departmentList);
    }

    @Override
    public void getDocumentTypeList(OnFinishedListenerDocument onFinishedListenerDocument) {
        ArrayList<String>documentTypeList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            documentTypeList.add("Document Type "+i);
        }
        onFinishedListenerDocument.onDocumentTypeFetched(documentTypeList);
    }

    @Override
    public void getDocumentDetailList(OnFinishedListenerSearchDocument onFinishedListenerSearchDocument) {
        ArrayList<DocumentDetailRequestModel> documentDetailRequestModels = new ArrayList<>();
        for (int i = 0;i<10;i++){
            documentDetailRequestModels.add(new DocumentDetailRequestModel("200100300/100/2019"+i,"19/08/201"+i,2000000+i*100));
        }
        onFinishedListenerSearchDocument.onDocumentDetailFetched(documentDetailRequestModels);
    }

    @Override
    public void getTransactionTypeList(OnFinishedListenerFund onFinishedListenerFund) {
        ArrayList<String>transactionTypeList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            transactionTypeList.add("Transaction Type "+i);
        }
        onFinishedListenerFund.onTransactionTypeFetched(transactionTypeList);
    }


}
