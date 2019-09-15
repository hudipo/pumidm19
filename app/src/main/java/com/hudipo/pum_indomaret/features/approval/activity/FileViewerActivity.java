package com.hudipo.pum_indomaret.features.approval.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hudipo.pum_indomaret.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_viewer);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }
}
