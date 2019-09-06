package com.hudipo.pum_indomaret.features.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.activity.ResponseActivity;

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
        Toast.makeText(this, "Request", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnApproval)
    public void clickApproval(View view){
        Toast.makeText(this, "Approval", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnInbox)
    public void clickInbox(View view){
        Toast.makeText(this, "Inbox", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnResponse)
    public void clickResponse(View view){
        startActivity(new Intent(this, ResponseActivity.class));
    }

    @OnClick(R.id.btnSetting)
    public void clickSetting(View view){
        Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnReport)
    public void clickReport(View view){
        Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();
    }
}
