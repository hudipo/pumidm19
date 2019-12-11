package com.hudipo.pum_indomaret.features.approval.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.utils.Extra;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalSuccessActivity extends AppCompatActivity {
    @BindView(R.id.animation)
    LottieAnimationView animation;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_success);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        int type = getIntent().getIntExtra(Extra.EXTRA_APPROVAL_HISTORY_TYPE, -1);
        if(type==0){ //rejected
            animation.setAnimation("rejected.json");
            tvTitle.setText(getString(R.string.request_rejected));
            tvDescription.setText(getString(R.string.request_rejected_desc));
        }else {
            animation.setAnimation("approved.json");
            tvTitle.setText(getString(R.string.request_approved));
            tvDescription.setText(getString(R.string.request_approved_note));
        }
    }

    @OnClick(R.id.btnViewHistory)
    public void btnViewHistory(){
        Intent intent = new Intent(this, ApprovalActivity.class);
        intent.putExtra("isLoadHistory", true);
        startActivity(intent);
//        StartActivity.goTo(this, StatusActivity.class);
        finish();
    }

    @OnClick(R.id.btnBackHomeRequestSent)
    public void btnBackHomeRequestSent(){
        StartActivity.goTo(this, HomeActivity.class);
        finishAffinity();
    }
}
