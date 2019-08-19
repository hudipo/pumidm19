package com.hudipo.pum_indomaret.features.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.login.activity.LoginActivity;
import com.hudipo.pum_indomaret.utils.StartActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        delayAndGoTo();
    }

    private void delayAndGoTo() {
        new Handler().postDelayed(() -> {
            StartActivity.goTo(this, LoginActivity.class);
            finish();
        },1200);
    }
}
