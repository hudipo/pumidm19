package com.hudipo.pum_indomaret.features.response.subfeatures.partialresponsesent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.response.activity.ResponseActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsepartial.ResponsePartialActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PartialResponseSentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partial_response_sent);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCreateAnotherResponse)
    void btnCreateAnotherResponse(){
        startActivity(new Intent(this, ResponseActivity.class));
        finish();
        Animatoo.animateSlideRight(this);

    }

    @OnClick(R.id.btnBackHome)
    void btnBackHome(){
        startActivity(new Intent(this, HomeActivity.class));
        finishAffinity();
        Animatoo.animateSlideUp(this);

    }

    @Override
    public void onBackPressed(){
        // super.onBackPressed();

    }
}
