package com.hudipo.pum_indomaret.features.setting.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePinSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin_success);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnBackHome)
    void backHome(){
        StartActivity.goTo(this, HomeActivity.class);
        finishAffinity();
    }
}
