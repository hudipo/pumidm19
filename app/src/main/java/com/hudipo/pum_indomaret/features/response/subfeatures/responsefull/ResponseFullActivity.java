package com.hudipo.pum_indomaret.features.response.subfeatures.responsefull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.fullresponseadded.FullResponseAddedActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsecartfull.ResponseCartFullActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsecartpartial.ResponseCartPartialActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseFullActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_full);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAdd)
    void btnAdd(){
        startActivity(new Intent(this, FullResponseAddedActivity.class));
    }

    @OnClick(R.id.btnFolder)
    void btnFolder(){
        startActivity(new Intent(this, ResponseCartFullActivity.class));
    }

    @OnClick(R.id.btnTransactionType)
    void btnTransactionType(){
        Toast.makeText(this, "Do something", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnUploadFile)
    void btnUploadFile(){
        Toast.makeText(this, "Upload File", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }
}
