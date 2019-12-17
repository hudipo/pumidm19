package com.hudipo.pum_indomaret.features.report.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.report.contract.ReportContract;
import com.hudipo.pum_indomaret.features.report.presenter.ReportPresenterImpl;
import com.hudipo.pum_indomaret.utils.PumDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity implements ReportContract.ReportView {

    public static final String FILE_PATH = "filePath";
    private static final int START_DATE_CODE = 0;
    private static final int END_DATE_CODE = 1;
    private static final int VALID_START_DATE_CODE = 2;
    private static final int VALID_END_DATE_CODE = 3;
    private static int dateCode;
    Map<String,Integer> reportTypeMap = new HashMap<>();
    Map<String,String> pumStatusMap = new HashMap<>();
    Map<String,String> respStatusMap = new HashMap<>();
    Map<String,String>groupByMap = new HashMap<>();

    @BindView(R.id.parentView)
    ConstraintLayout parentView;

    @BindView(R.id.pbReport)
    LottieAnimationView progressBar;
    @BindView(R.id.spnReportTypeRep)
    Spinner spnReportType;
    @BindView(R.id.spnPumStatus)
    Spinner spnPumStatus;
    @BindView(R.id.spnRespStatus)
    Spinner spnRespStatus;
    @BindView(R.id.spnGroupBy)
    Spinner spnGroupBy;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;
    @BindView(R.id.tvEndDate)
    TextView tvEndDate;
    @BindView(R.id.tvValidStartDate)
    TextView tvValidStartDate;
    @BindView(R.id.tvValidEndDate)
    TextView tvValidEndDate;
    @BindView(R.id.tvPumStatusLabel)
    TextView tvPumStatusLabel;
    @BindView(R.id.tvRespStatusLabel)
    TextView tvRespStatusLabel;
    @BindView(R.id.tvGroupByLabel)
    TextView tvGroupByLabel;
    ImageView pumStatusArrow;
    @OnClick(R.id.btnViewRep)
    void viewRep(){
        if (progressBar.getVisibility()==View.GONE){
            if (formValidity(tvStartDate.getText().toString(),tvEndDate.getText().toString(),tvValidStartDate.getText().toString(),tvValidEndDate.getText().toString())){
                callReportApi(false);
            }else {
                toast("Please fill all the form!");
            }
        }
    }

    private void callReportApi(boolean b) {
        if (spnReportType.getSelectedItem().toString().equals("Pertanggungjawaban PUM")){
            presenter.checkData(2,
                    PumDateFormat.dateFormatServer(tvStartDate.getText().toString().trim()),
                    PumDateFormat.dateFormatServer(tvEndDate.getText().toString().trim()),
                    PumDateFormat.dateFormatServer(tvValidStartDate.getText().toString().trim()),
                    PumDateFormat.dateFormatServer(tvValidEndDate.getText().toString().trim()),
                    "I",
                    respStatusMap.get(spnRespStatus.getSelectedItem().toString()),
                    "-");
        }else {
            presenter.checkData(1,
                    PumDateFormat.dateFormatServer(tvStartDate.getText().toString().trim()),
                    PumDateFormat.dateFormatServer(tvEndDate.getText().toString().trim()),
                    PumDateFormat.dateFormatServer(tvValidStartDate.getText().toString().trim()),
                    PumDateFormat.dateFormatServer(tvValidEndDate.getText().toString().trim()),
                    pumStatusMap.get(spnPumStatus.getSelectedItem().toString()),
                    respStatusMap.get(spnRespStatus.getSelectedItem().toString()),
                    groupByMap.get(spnGroupBy.getSelectedItem().toString()));
        }
    }


    @OnClick(R.id.imgBack)
    void onBackClick(){
        super.onBackPressed();
    }

    @OnClick(R.id.imgStartDate)
    void onImgStartDateClicked(){
        dateCode = START_DATE_CODE;
        presenter.initDatePicker();
    }

    @OnClick(R.id.imgValidStartDate)
    void onImgValidStartDate(){
        dateCode = VALID_START_DATE_CODE;
        presenter.initDatePicker();
    }
    @OnClick(R.id.imgValidEndDate)
    void onImgValidEndDate(){
        dateCode = VALID_END_DATE_CODE;
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
        presenter = new ReportPresenterImpl(this, this);
        mDateSetListener = (view, year, month, dayOfMonth) -> presenter.onDateSet(year, month, dayOfMonth);
        setViewData();
    }

    @Override
    public void setDate(String date) {
        switch (dateCode){
            case START_DATE_CODE :
                tvStartDate.setText(date);
                break;
            case END_DATE_CODE :
                tvEndDate.setText(date);
                break;
            case VALID_START_DATE_CODE :
                tvValidStartDate.setText(date);
                break;
            case VALID_END_DATE_CODE :
                tvValidEndDate.setText(date);
                break;
        }
    }

    public void setViewData() {
        ArrayList<String> reportTypeList = new ArrayList<>();
        reportTypeList.add("Permohonan Uang Muka");
        reportTypeList.add("Pertanggungjawaban PUM");

        ArrayList<String> pumStatusList = new ArrayList<>();
        pumStatusList.add("All");
        pumStatusList.add("New");
        pumStatusList.add("Approve 1");
        pumStatusList.add("Approve 2");
        pumStatusList.add("Approve 3");
        pumStatusList.add("Approve 4");
        pumStatusList.add("Approved");
        pumStatusList.add("Invoiced");

        ArrayList<String> respStatusList = new ArrayList<>();
        respStatusList.add("All");
        respStatusList.add("New");
        respStatusList.add("Full");
        respStatusList.add("Partial");
        respStatusList.add("Invoiced");

        ArrayList<String> groupByList = new ArrayList<>();
        groupByList.add("-");
        groupByList.add("Employee");
        groupByList.add("Department");
        groupByList.add("Create Data");

        reportTypeMap.put("Pertanggungjawaban PUM",2);
        reportTypeMap.put("Permohonan Uang Muka",1);

        pumStatusMap.put("New","N");
        pumStatusMap.put("Approve 1","App1");
        pumStatusMap.put("Approve 2","App2");
        pumStatusMap.put("Approve 3","App3");
        pumStatusMap.put("Approve 4","App4");
        pumStatusMap.put("Approved","A");
        pumStatusMap.put("Invoiced","I");
        pumStatusMap.put("All","ALL");

        respStatusMap.put("All","ALL");
        respStatusMap.put("New","N");
        respStatusMap.put("Full","F");
        respStatusMap.put("Partial", "P");
        respStatusMap.put("Invoiced","I");

        groupByMap.put("-","-");
        groupByMap.put("Employee","E");
        groupByMap.put("Department","D");
        groupByMap.put("Create Data","C");


        ArrayAdapter<String> spnReportTypeAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, reportTypeList);
        spnReportTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnReportType.setAdapter(spnReportTypeAdapter);
        spnReportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnReportType.getSelectedItem().toString().equals("Pertanggungjawaban PUM")){
                    spnPumStatus.setVisibility(View.GONE);
                    spnGroupBy.setVisibility(View.GONE);
                    tvPumStatusLabel.setVisibility(View.GONE);
                    tvGroupByLabel.setVisibility(View.GONE);
                }else {
                    spnPumStatus.setVisibility(View.VISIBLE);
                    spnGroupBy.setVisibility(View.VISIBLE);
                    tvPumStatusLabel.setVisibility(View.VISIBLE);
                    tvGroupByLabel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> pumStatusAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, pumStatusList);
        pumStatusAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnPumStatus.setAdapter(pumStatusAdapter);

        ArrayAdapter<String> respStatusAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, respStatusList);
        respStatusAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnRespStatus.setAdapter(respStatusAdapter);

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
    public void toast(String message) {
        progressBar.setVisibility(View.GONE);
        new AlertDialog.Builder(this,R.style.CustomDialogTheme)
                .setTitle("Attention!")
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    @Override
    public void showLoading() {
        parentView.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        parentView.setClickable(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showPdf(Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri,"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }else {
            toast("You don't have pdf viewer!");
        }
    }

    @Override
    public void askToDownload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialogTheme);
        builder.setTitle("View")
                .setMessage("This report will be downloaded as a PDF file and saved on your phone")
                .setNeutralButton("Download", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    presenter.downloadData();
                })
                .setPositiveButton("Later", (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        nbutton.setTextColor(getResources().getColor(R.color.btnDownload));
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(getResources().getColor(R.color.redError));
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private boolean formValidity(String startDate, String endDate, String validStartDate, String validEndDate){
        return startDate != null && endDate != null && validStartDate != null && validEndDate != null && startDate.length() > 0
                && endDate.length() > 0 && validStartDate.length() > 0 && validEndDate.length() > 0;
    }
}
