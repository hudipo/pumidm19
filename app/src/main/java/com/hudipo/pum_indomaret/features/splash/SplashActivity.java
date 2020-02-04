package com.hudipo.pum_indomaret.features.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.login.LoginActivity;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.StartActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        delayAndGoTo();
        Log.d("fakhri", "onCreate: "+getIntent().getExtras());

    }

    private void checkLogin() {
        HawkStorage hawkStorage = new HawkStorage(this);
        if (hawkStorage.getLogin()){
            Intent intent = new Intent(this,HomeActivity.class);
            intent.putExtra("kodeNotif", getIntent().getStringExtra("kodeNotif"));
            startActivity(intent);
            finish();
        }else {
            StartActivity.goTo(this, LoginActivity.class);
            finish();
        }
    }

    private void delayAndGoTo() {
        new Handler().postDelayed(this::checkLogin,1000);
    }
}
