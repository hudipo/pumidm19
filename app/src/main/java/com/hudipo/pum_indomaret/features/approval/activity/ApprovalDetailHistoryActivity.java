package com.hudipo.pum_indomaret.features.approval.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hudipo.pum_indomaret.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_APPROVAL_HISTORY_TYPE;

public class ApprovalDetailHistoryActivity extends AppCompatActivity {
    @BindView(R.id.ivType)
    ImageView ivType;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_detail_history);

        ButterKnife.bind(this);
        type = getIntent().getIntExtra(EXTRA_APPROVAL_HISTORY_TYPE, 1);

        setView();
    }

    private void setView() {
        if(type==1){
            ivType.setImageResource(R.drawable.txt_approved);
        }else ivType.setImageResource(R.drawable.txt_rejected);
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }
}
