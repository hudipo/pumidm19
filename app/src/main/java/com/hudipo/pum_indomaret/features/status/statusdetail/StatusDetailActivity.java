package com.hudipo.pum_indomaret.features.status.statusdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hudipo.pum_indomaret.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        onBackPressed();
    }
}
