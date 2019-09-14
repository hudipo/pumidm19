package com.hudipo.pum_indomaret.features.requestpum.model;

import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;

import java.util.ArrayList;

public class RequestIntractorImpl implements RequestContract.RequestIntractor {

    @Override
    public void getEmployeeName(OnFinishedListener onFinishedListener) {
        onFinishedListener.onEmployeeNameFetched("Ade Kurniawan");
    }

    @Override
    public void getDepartmentList(OnFinishedListener onFinishedListener) {
        ArrayList<String>departmentList = new ArrayList<>();
        for (int i=0;i<10;i++){
            departmentList.add("Department "+i);
        }
        onFinishedListener.onDepartmentFetched(departmentList);
    }

    @Override
    public void getDocumentTypeList(OnFinishedListener onFinishedListener) {
        ArrayList<String>documentTypeList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            documentTypeList.add("Document Type "+i);
        }
        onFinishedListener.onDocumentTypeFetched(documentTypeList);
    }
}
