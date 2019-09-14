package com.hudipo.pum_indomaret.features.requestpum.presenter;

import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;

import java.util.ArrayList;
import java.util.Calendar;

public class EmployeePresenterImpl implements RequestContract.EmployeePresenter, RequestContract.RequestIntractor.OnFinishedListener {

    private RequestContract.EmployeeView employeeView;
    private RequestContract.RequestIntractor intractor;

    public EmployeePresenterImpl (RequestContract.EmployeeView employeeView, RequestContract.RequestIntractor intractor){
        this.employeeView = employeeView;
        this.intractor = intractor;
    }

    @Override
    public void onDestroy() {
        this.employeeView = null;
    }

    @Override
    public void formValidation() {

    }

    @Override
    public void getDepartmentList() {
        intractor.getDepartmentList(this);
    }

    @Override
    public void getEmployeeName() {
        intractor.getEmployeeName(this);
    }

    @Override
    public void onDateSet(int year, int month, int dayOfMonth,int dateCode) {
        month = month + 1;
        String date = dayOfMonth + "/" + month + "/" + year;
       employeeView.setDate(date,dateCode);
    }

    @Override
    public void onDateClicked(int dateCode) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        employeeView.displayDatePickerDialog(year, month, day,dateCode);
    }

    @Override
    public void onDepartmentFetched(ArrayList<String> departmentList) {
        employeeView.setDepartmentList(departmentList);
    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onEmployeeNameFetched(String employeName) {
        employeeView.setEmployeeName(employeName);
    }

    @Override
    public void onDocumentTypeFetched(ArrayList<String> documentTypeList) {

    }
}
