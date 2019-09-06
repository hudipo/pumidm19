package com.hudipo.pum_indomaret.features.response.subfeatures.partialresponseadded;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsecartpartial.ResponseCartPartialActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PartialResponseAddedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partial_response_added);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnViewResponseCart)
    void btnViewResponseCart(){
        startActivity(new Intent(this, ResponseCartPartialActivity.class));
        finish();
    }

    @OnClick(R.id.btnAddAnotherResponse)
    void btnAddAnotherResponse(){
        finish();
    }
}
