package com.hudipo.pum_indomaret.features.status.statusdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.status.StatusActivity;
import com.hudipo.pum_indomaret.features.status.adapter.ApproverAdapter;
import com.hudipo.pum_indomaret.features.status.model.Approver;
import com.hudipo.pum_indomaret.features.status.model.StatusDetailResponse;
import com.hudipo.pum_indomaret.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusDetailActivity extends AppCompatActivity {

    private StatusDetailResponse.StatusDetailModel statusDetailModel;

    @BindView(R.id.tvPumNumber)
    public TextView tvPumNumber;

    @BindView(R.id.tvDepartmentStatus)
    public TextView tvDepartment;

    @BindView(R.id.tvTrxDate)
    public TextView tvTrxDate;

    @BindView(R.id.tvUseDate)
    public TextView tvUseDate;

    @BindView(R.id.tvRespDate)
    public TextView tvRespDate;

    @BindView(R.id.tvTransactionType)
    public TextView tvTrxType;

    @BindView(R.id.tvAmount)
    public TextView tvAmount;

    @BindView(R.id.tvDescription)
    public TextView tvDesc;

    @BindView(R.id.tvReason)
    public TextView tvReason;

    @BindView(R.id.textViewReason)
    public TextView textViewReason;

    @BindView(R.id.rvApprover1)
    public RecyclerView rvApprover1;

    @BindView(R.id.rvApprover2)
    public RecyclerView rvApprover2;

    @BindView(R.id.rvApprover3)
    public RecyclerView rvApprover3;

    @BindView(R.id.rvApprover4)
    public RecyclerView rvApprover4;

    @BindView(R.id.rvApprover5)
    public RecyclerView rvApprover5;

    private ApproverAdapter adapterApprover1,adapterApprover2,adapterApprover3,adapterApprover4,adapterApprover5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);
        ButterKnife.bind(this);
        if (getIntent()!=null){
            Bundle bundle = getIntent().getExtras();
            assert bundle != null;
            statusDetailModel = (StatusDetailResponse.StatusDetailModel) bundle.getSerializable(StatusActivity.STATUS_DETAIL);
            setView();

        }
    }

    private void setView() {

        if (statusDetailModel!=null){
            tvPumNumber.setText(statusDetailModel.trxNum);
            tvDepartment.setText(statusDetailModel.department);
            tvTrxDate.setText(statusDetailModel.trxDate);
            tvUseDate.setText(statusDetailModel.useDate);
            tvRespDate.setText(statusDetailModel.respEstimateDate);
            tvTrxType.setText(statusDetailModel.pumTrxTypeId);
            tvAmount.setText(Utils.convertLongToCurrency(statusDetailModel.amount));
            tvDesc.setText(statusDetailModel.description);
            tvReason.setText(statusDetailModel.reason);
            if (!tvReason.getText().toString().matches("")){
                tvReason.setVisibility(View.VISIBLE);
                textViewReason.setVisibility(View.VISIBLE);

            }
            initRv();
        }
    }

    private void initRv() {
        if (statusDetailModel.dataApp.app1.size()<2){
            for (int i=statusDetailModel.dataApp.app1.size();i<2;i++){
                statusDetailModel.dataApp.app1.add(new Approver(i,""));
            }
        }
        if (statusDetailModel.dataApp.app2.size()<2){
            for (int i=statusDetailModel.dataApp.app2.size();i<2;i++){
                statusDetailModel.dataApp.app2.add(new Approver(i,""));
            }
        }
        if (statusDetailModel.dataApp.app3.size()<2){
            for (int i=statusDetailModel.dataApp.app3.size();i<2;i++){
                statusDetailModel.dataApp.app3.add(new Approver(i,""));
            }
        }
        if (statusDetailModel.dataApp.app4.size()<2){
            for (int i=statusDetailModel.dataApp.app4.size();i<2;i++){
                statusDetailModel.dataApp.app4.add(new Approver(i,""));
            }
        }
        if (statusDetailModel.dataApp.app5.size()<2){
            for (int i=statusDetailModel.dataApp.app5.size();i<2;i++){
                statusDetailModel.dataApp.app5.add(new Approver(i,""));
            }
        }

        rvApprover1.setHasFixedSize(true);
        rvApprover1.setLayoutManager(new LinearLayoutManager(this));
        adapterApprover1 = new ApproverAdapter(statusDetailModel.dataApp.app1);
        rvApprover1.setAdapter(adapterApprover1);

        rvApprover2.setHasFixedSize(true);
        rvApprover2.setLayoutManager(new LinearLayoutManager(this));
        adapterApprover2 = new ApproverAdapter(statusDetailModel.dataApp.app2);
        rvApprover2.setAdapter(adapterApprover2);

        rvApprover3.setHasFixedSize(true);
        rvApprover3.setLayoutManager(new LinearLayoutManager(this));
        adapterApprover3 = new ApproverAdapter(statusDetailModel.dataApp.app3);
        rvApprover3.setAdapter(adapterApprover3);

        rvApprover4.setHasFixedSize(true);
        rvApprover4.setLayoutManager(new LinearLayoutManager(this));
        adapterApprover4 = new ApproverAdapter(statusDetailModel.dataApp.app4);
        rvApprover4.setAdapter(adapterApprover4);

        rvApprover5.setHasFixedSize(true);
        rvApprover5.setLayoutManager(new LinearLayoutManager(this));
        adapterApprover5 = new ApproverAdapter(statusDetailModel.dataApp.app5);
        rvApprover5.setAdapter(adapterApprover5);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(this); //fire the slide left animation
        finish();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        onBackPressed();
    }
}
