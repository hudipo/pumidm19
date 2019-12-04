package com.hudipo.pum_indomaret.features.approval.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.repository.Repository;
import com.hudipo.pum_indomaret.utils.DatePickerFragment;
import com.hudipo.pum_indomaret.utils.PumDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_START_DATE;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_STATUS;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_UNTIL_DATE;

public class ApprovalFilterActivity extends AppCompatActivity implements DatePickerFragment.DialogDataListener  {
    @BindView(R.id.ivTrxStartDate)
    ImageView ivTrxStartDate;
    @BindView(R.id.ivTrxUntilDate)
    ImageView ivTrxUntilDate;
    @BindView(R.id.tvTrxStartDate)
    TextView tvTrxStartDate;
    @BindView(R.id.tvTrxUntilDate)
    TextView tvTrxUntilDate;
    @BindView(R.id.spTrxStatus)
    Spinner spTrxStatus;

    private DatePickerFragment datePickerFragment = new DatePickerFragment();
    private static final String TAG_START_DATE = "tag_start_date";
    private static final String TAG_UNTIL_DATE = "tag_until_date";
    private String startDate;
    private String untilDate;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_filter);

        ButterKnife.bind(this);

        setClick();
        initSpinner();

        tvTrxStartDate.setText("---");
        tvTrxUntilDate.setText("---");
    }

    private void setClick() {
        ivTrxStartDate.setOnClickListener(view -> datePickerFragment.show(getSupportFragmentManager(), TAG_START_DATE));
        tvTrxStartDate.setOnClickListener(view -> datePickerFragment.show(getSupportFragmentManager(), TAG_START_DATE));
        ivTrxUntilDate.setOnClickListener(view -> datePickerFragment.show(getSupportFragmentManager(), TAG_UNTIL_DATE));
        tvTrxUntilDate.setOnClickListener(view -> datePickerFragment.show(getSupportFragmentManager(), TAG_UNTIL_DATE));
    }

    private void initSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, Repository.getDataStatusApproval(this));
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spTrxStatus.setAdapter(dataAdapter);
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }

    @OnClick(R.id.btnSubmit)
    void submit(){
        if(validate()){
            Intent returnIntent = new Intent();
            returnIntent.putExtra(EXTRA_START_DATE,startDate);
            returnIntent.putExtra(EXTRA_UNTIL_DATE,untilDate);
            status=spTrxStatus.getSelectedItem().toString();
            if(!status.equalsIgnoreCase("---")){
                String temp = "APP";
                if(status.equalsIgnoreCase("Rejected")){
                    temp = "R";
                }
                returnIntent.putExtra(EXTRA_STATUS,temp);
            }
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    private boolean validate() {
        startDate = tvTrxStartDate.getText().toString();
        untilDate = tvTrxStartDate.getText().toString();
        if (startDate.equals("---")){
            toast("Please Select Start Date!");
        }else if (untilDate.equals("---")){
            toast("Please Select Until Date");
        }else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                Date startDateFormat = sdf.parse(startDate);
                Date untilDateFormat = sdf.parse(untilDate);
                long todayInMilis = Calendar.getInstance().getTimeInMillis();
                long startDateDiffMil = todayInMilis - startDateFormat.getTime();
                long untilDateDiffMil = todayInMilis - untilDateFormat.getTime();
                long startDateDiffDay = TimeUnit.MILLISECONDS.toDays(startDateDiffMil);
                long untilDateDiffDay = TimeUnit.MILLISECONDS.toDays(untilDateDiffMil);
                if (startDateDiffDay>31 || untilDateDiffDay>31){
                    toast("Please Input a Valid Date!");
                }else{
                    return true;
                }

            } catch (ParseException e) {
                toast(e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public void onDialogSet(String tag, int year, int month, int dayOfMonth) {
        String time = PumDateFormat.dateFormatView(year, month, dayOfMonth);
        if (tag.equals(TAG_START_DATE)){
            tvTrxStartDate.setText(time);
        }else {
            tvTrxUntilDate.setText(time);
        }
    }

    public void toast(String stringMessage) {
        new AlertDialog.Builder(this,R.style.CustomDialogTheme)
                .setTitle("Attention!")
                .setMessage(stringMessage)
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }
}
