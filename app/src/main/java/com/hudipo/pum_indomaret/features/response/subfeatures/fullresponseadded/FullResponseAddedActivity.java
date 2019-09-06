package com.hudipo.pum_indomaret.features.response.subfeatures.fullresponseadded;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsecartfull.ResponseCartFullActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullResponseAddedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_response_added);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnViewResponseCart)
    void btnViewResponseCart(){
        startActivity(new Intent(this, ResponseCartFullActivity.class));
        finish();
    }

    @OnClick(R.id.btnAddAnotherResponse)
    void btnAddAnotherResponse(){
        finish();
    }
}
