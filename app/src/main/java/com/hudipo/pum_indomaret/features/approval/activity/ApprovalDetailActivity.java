package com.hudipo.pum_indomaret.features.approval.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_detail);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }

    @OnClick(R.id.btnDetail)
    void showImageFile(){
        StartActivity.goTo(this, FileViewerActivity.class);
    }
}
