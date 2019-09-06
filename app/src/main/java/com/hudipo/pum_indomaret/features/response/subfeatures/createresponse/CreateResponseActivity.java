package com.hudipo.pum_indomaret.features.response.subfeatures.createresponse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsefull.ResponseFullActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsepartial.ResponsePartialActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_response);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnBack)
    void back(){
        finish();
    }

    @OnClick(R.id.btnFull)
    void btnFull(){
        startActivity(new Intent(this, ResponseFullActivity.class));
    }

    @OnClick(R.id.btnPartial)
    void btnPartial(){
        startActivity(new Intent(this, ResponsePartialActivity.class));
    }
}
