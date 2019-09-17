package com.hudipo.pum_indomaret.features.approval.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalFilterActivity extends AppCompatActivity {
    @BindView(R.id.ivTrxStartDate)
    ImageView ivTrxStartDate;
    @BindView(R.id.ivTrxUntilDate)
    ImageView ivTrxUntilDate;
    @BindView(R.id.tvTrxStartDate)
    TextView tvTrxStartDate;
    @BindView(R.id.tvTrxUntilDate)
    TextView tvTrxUntilDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_filter);

        ButterKnife.bind(this);

        setClick();
    }

    private void setClick() {
        ivTrxStartDate.setOnClickListener(view -> launchDatePicker(tvTrxStartDate));
        tvTrxStartDate.setOnClickListener(view -> launchDatePicker(tvTrxStartDate));
        ivTrxUntilDate.setOnClickListener(view -> launchDatePicker(tvTrxUntilDate));
        tvTrxUntilDate.setOnClickListener(view -> launchDatePicker(tvTrxUntilDate));
    }

    private void launchDatePicker(TextView textView) {
        final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        if(!textView.getText().toString().isEmpty()){
            try {
                calendar.setTime(Objects.requireNonNull(dateFormatter.parse(textView.getText().toString())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            textView.setText(dateFormatter.format(newDate.getTime()));
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }
}
