package com.hudipo.pum_indomaret.features.status;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.StatusFilterInteractorImpl;
import com.hudipo.pum_indomaret.features.status.model.StatusFilterRequestBody;
import com.hudipo.pum_indomaret.features.status.presenter.StatusFilterPresenterImpl;
import com.orhanobut.hawk.Hawk;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class StatusFilterActivity extends AppCompatActivity implements StatusContract.StatusFilterView {

    public static final int START_DATE_CODE = 0;
    public static final int UNTIL_DATE_CODE = 1;
    private StatusContract.StatusFilterPresenter presenter;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private StatusFilterRequestBody data = new StatusFilterRequestBody();
    ArrayAdapter<String> dataAdapter;

    @BindView(R.id.spnTrxStatus)
    Spinner spnTrxStatus;
    @BindView(R.id.tvTrxStartDate)
    TextView tvTrxStartDate;
    @BindView(R.id.tvTrxUntilDate)
    TextView tvTrxUntilDate;
    @OnClick(R.id.imgStartDate)
    void startDateClicked(){
        presenter.onStartDateClicked();
    }
    @OnClick(R.id.imgUntilDate)
    void untilDateClicked(){
        presenter.onUntilDateClicked();
    }
    @OnClick(R.id.imgBack)
    void btnBack(){
        super.onBackPressed();
    }
    @OnClick(R.id.btnView)
    void validate(){
        presenter.validateDate(tvTrxStartDate.getText().toString(),tvTrxUntilDate.getText().toString(),spnTrxStatus.getSelectedItem().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_filter);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        presenter = new StatusFilterPresenterImpl(this, new StatusFilterInteractorImpl(this));
        presenter.getStatusList();
        datePickerListener = (datePicker, year, month, day) -> presenter.onDateSet(year,month,day);

    }
    @Override
    public void setStartDate(String date) {
        tvTrxStartDate.setText(date);
    }

    @Override
    public void setUntilDate(String date) {
        tvTrxUntilDate.setText(date);
    }

    @Override
    public void setStatusList(ArrayList<String> statusList) {
        dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, statusList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnTrxStatus.setAdapter(dataAdapter);
    }

    @Override
    public void setDatePickerView(int year, int month, int day) {
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerListener,
                year, day, month);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void goBackToStatusAct(String startDate, String untilDate, String status) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("startDate",startDate);
        returnIntent.putExtra("untilDate",untilDate);
        returnIntent.putExtra("status",status);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }

    @Override
    public void toast(String stringMessage) {
        Toast.makeText(this, stringMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


}
