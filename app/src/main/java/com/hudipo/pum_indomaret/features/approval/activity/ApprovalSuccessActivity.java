package com.hudipo.pum_indomaret.features.approval.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.utils.Extra;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalSuccessActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
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
            imageView.setImageDrawable(getDrawable(R.drawable.rejected));
            tvTitle.setText(getString(R.string.request_rejected));
            tvDescription.setText(getString(R.string.request_rejected_desc));
        }else {
            imageView.setImageDrawable(getDrawable(R.drawable.approved));
            tvTitle.setText(getString(R.string.request_approved));
            tvDescription.setText(getString(R.string.request_approved_note));
        }
    }

    @OnClick(R.id.btnViewHistory)
    public void btnViewHistory(){
//        StartActivity.goTo(this, StatusActivity.class);
        finish();
    }

    @OnClick(R.id.btnBackHomeRequestSent)
    public void btnBackHomeRequestSent(){
        StartActivity.goTo(this, HomeActivity.class);
        finishAffinity();
    }
}
