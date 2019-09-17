package com.hudipo.pum_indomaret.features.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalActivity;
import com.hudipo.pum_indomaret.features.report.view.ReportActivity;
import com.hudipo.pum_indomaret.features.requestpum.view.EmployeeReqActivity;
import com.hudipo.pum_indomaret.features.response.activity.ResponseActivity;
import com.hudipo.pum_indomaret.features.setting.activity.SettingActivity;
import com.hudipo.pum_indomaret.features.status.StatusActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRequest)
    public void clickRequest(View view){
        startActivity(new Intent(this, EmployeeReqActivity.class));
    }

    @OnClick(R.id.btnApproval)
    public void clickApproval(View view){
        startActivity(new Intent(this, ApprovalActivity.class));
    }

    @OnClick(R.id.btnStatus)
    public void clickInbox(View view){
        startActivity(new Intent(this, StatusActivity.class));
    }

    @OnClick(R.id.btnResponse)
    public void clickResponse(View view){
        startActivity(new Intent(this, ResponseActivity.class));
    }

    @OnClick(R.id.btnSetting)
    public void clickSetting(View view){
        startActivity(new Intent(this, SettingActivity.class));
    }

    @OnClick(R.id.btnReport)
    public void clickReport(View view){
        startActivity(new Intent(this, ReportActivity.class));
    }
}