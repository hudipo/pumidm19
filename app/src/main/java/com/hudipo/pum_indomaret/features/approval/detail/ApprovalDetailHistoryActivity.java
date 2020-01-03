package com.hudipo.pum_indomaret.features.approval.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.detail.presenter.ApprovalDetailPresenter;
import com.hudipo.pum_indomaret.features.approval.detail.view.ApprovalDetailContract;
import com.hudipo.pum_indomaret.model.approval.detail.DataApproval;
import com.hudipo.pum_indomaret.utils.Global;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PUM_STATUS;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PUM_TRX_ID;

public class ApprovalDetailHistoryActivity extends AppCompatActivity implements ApprovalDetailContract.ApprovalDetailView {
    @BindView(R.id.ivType)
    ImageView ivType;
    @BindView(R.id.tvPumNumber)
    TextView tvPumNumber;
    @BindView(R.id.tvPumRequester)
    TextView tvPumRequester;
    @BindView(R.id.tvDepartment)
    TextView tvDepartment;
    @BindView(R.id.tvTrxDate)
    TextView tvTrxDate;
    @BindView(R.id.tvUseDate)
    TextView tvUseDate;
    @BindView(R.id.tvTransactionType)
    TextView tvTransactionType;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvReason)
    TextView tvReason;
    @BindView(R.id.tvReasonTitle)
    TextView tvReasonTitle;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.loading)
    LottieAnimationView loading;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private ApprovalDetailPresenter presenter;
    private Integer pumTrxId;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_detail_history);

        ButterKnife.bind(this);
        pumTrxId = getIntent().getIntExtra(EXTRA_PUM_TRX_ID, 0);
        status = getIntent().getStringExtra(EXTRA_PUM_STATUS);
        presenter = new ApprovalDetailPresenter(this);

        onAttachView();
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }

    @Override
    public void showData(DataApproval dataApproval) {
        tvPumNumber.setText(dataApproval.getTrxNum());
        tvPumRequester.setText(dataApproval.getName());
        tvDepartment.setText(dataApproval.getDepartment());
        tvTrxDate.setText(dataApproval.getTrxDate());
        tvUseDate.setText(dataApproval.getUseDate());
        tvTransactionType.setText(dataApproval.getPumTrxTypeId());
        tvDescription.setText(dataApproval.getDescription());
        tvReason.setText(dataApproval.getReason());
        tvAmount.setText(String.format("%s %s", getString(R.string.rp), Global.priceFormatter(String.valueOf(dataApproval.getAmount()))));
        scrollView.setVisibility(View.VISIBLE);

        if(status.equalsIgnoreCase("R"))
        {
            ivType.setImageResource(R.drawable.txt_rejected);

        }
        else {
            ivType.setImageResource(R.drawable.txt_approved);
            tvReason.setVisibility(View.GONE);
            tvReasonTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void error(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void success(int message) {

    }

    @Override
    public void onAttachView() {
        presenter.onAttach(this);
        presenter.getData(pumTrxId);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }
}
