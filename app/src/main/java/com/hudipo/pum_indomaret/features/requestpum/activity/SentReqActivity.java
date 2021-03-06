package com.hudipo.pum_indomaret.features.requestpum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.status.StatusActivity;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SentReqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_sent);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnViewRequestSent)
    public void btnViewRequestSent(){
        StartActivity.goTo(this, StatusActivity.class);
        Animatoo.animateZoom(this);
        finish();
    }

    @OnClick(R.id.btnBackHomeRequestSent)
    public void btnBackHomeRequestSent(){
        StartActivity.goTo(this, HomeActivity.class);
        Animatoo.animateSlideRight(this);
        finishAffinity();
    }

    @Override
    public void onBackPressed(){
//        super.onBackPressed();
//        Animatoo.animateSlideRight(this); //fire the slide left animation
//        finishAffinity();
    }
}
