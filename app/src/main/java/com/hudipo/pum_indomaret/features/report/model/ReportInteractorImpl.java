package com.hudipo.pum_indomaret.features.report.model;

import com.hudipo.pum_indomaret.features.report.contract.ReportContract;

import java.util.ArrayList;

public class ReportInteractorImpl implements ReportContract.ReportInteractor {
    @Override
    public void getViewData(OnFinishedListener onFinishedListener) {

        ArrayList<String> reportTypeList = new ArrayList<>();
        for (int i=0;i<10;i++){
            reportTypeList.add("Report Type "+i);
        }
        reportTypeList.add("uhsabfalsbasibfasufyhs");

        ArrayList<String>employeeNameList = new ArrayList<>();
        for (int i=0;i<10;i++){
            employeeNameList.add("Employee Name "+i);
        }

        ArrayList<String>departmentList = new ArrayList<>();
        for (int i=0;i<10;i++){
            departmentList.add("Department "+i);
        }

        ArrayList<String>groupByList = new ArrayList<>();
        for (int i=0;i<10;i++){
            groupByList.add("Group By "+i);
        }

        onFinishedListener.onDataFetched(reportTypeList,employeeNameList,departmentList,groupByList);
    }
}
