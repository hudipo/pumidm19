package com.hudipo.pum_indomaret.features.report.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.report.contract.ReportContract;
import com.hudipo.pum_indomaret.features.report.model.ReportInteractorImpl;
import com.hudipo.pum_indomaret.features.report.presenter.ReportPresenterImpl;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity implements ReportContract.ReportView {

    private static final int START_DATE_CODE = 0;
    private static final int END_DATE_CODE = 1;
    private static int dateCode;

    @BindView(R.id.spnReportTypeRep)
    Spinner spnReportType;

    @BindView(R.id.spnGroupBy)
    Spinner spnGroupBy;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;
    @BindView(R.id.tvEndDate)
    TextView tvEndDate;

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideRight(this); //fire the slide left animation
        finish();

    }

    @OnClick(R.id.imgBack)
    void onBackClick(){
        onBackPressed();
    }

    @OnClick(R.id.imgStartDate)
    void onImgStartDateClicked(){
        dateCode = START_DATE_CODE;
        presenter.initDatePicker();
    }

    @OnClick(R.id.imgEndDate)
    void onImgEndDateClicked(){
        dateCode = END_DATE_CODE;
        presenter.initDatePicker();
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private ReportPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        presenter = new ReportPresenterImpl(this, new ReportInteractorImpl());
        presenter.getViewData();
        mDateSetListener = (view, year, month, dayOfMonth) -> presenter.onDateSet(year, month, dayOfMonth);
    }

    @Override
    public void setDate(String date) {
        if (dateCode==START_DATE_CODE){
            tvStartDate.setText(date);
        }else tvEndDate.setText(date);
    }

    @Override
    public void setViewData(ArrayList<String> reportTypeList, ArrayList<String> employeeNameList, ArrayList<String> departmentList, ArrayList<String> groupByList) {
        ArrayAdapter<String> spnReportTypeAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, reportTypeList);
        spnReportTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnReportType.setAdapter(spnReportTypeAdapter);

        ArrayAdapter<String> spnEmployeeNameAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, employeeNameList);
        spnEmployeeNameAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);


        ArrayAdapter<String> spnDepartmenAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, departmentList);
        spnDepartmenAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);


        ArrayAdapter<String> spnGroupByAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, groupByList);
        spnGroupByAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnGroupBy.setAdapter(spnGroupByAdapter);
    }

    @Override
    public void showDatePicker(int day, int month, int year) {
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                year, month, day);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
