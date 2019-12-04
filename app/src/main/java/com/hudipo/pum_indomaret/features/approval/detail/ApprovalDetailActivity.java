package com.hudipo.pum_indomaret.features.approval.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalSuccessActivity;
import com.hudipo.pum_indomaret.features.approval.activity.FileViewerActivity;
import com.hudipo.pum_indomaret.features.approval.detail.presenter.ApprovalDetailPresenter;
import com.hudipo.pum_indomaret.features.approval.detail.view.ApprovalDetailContract;
import com.hudipo.pum_indomaret.features.pin.PinActivity;
import com.hudipo.pum_indomaret.model.approval.detail.DataApproval;
import com.hudipo.pum_indomaret.utils.Extra;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.StartActivity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PUM_TRX_ID;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_STATUS;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_URL;

public class ApprovalDetailActivity extends AppCompatActivity implements ApprovalDetailContract.ApprovalDetailView {
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
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvFileUploaded)
    TextView tvFileUploaded;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    private ApprovalDetailPresenter presenter;
    private Integer pumTrxId;
    private int requestType=-1;
    private final int REQUEST_CODE_PIN = 100;
    private String reasonValidation="";
    private DataApproval dataApproval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_detail);

        pumTrxId = getIntent().getIntExtra(EXTRA_PUM_TRX_ID, 0);
        presenter = new ApprovalDetailPresenter(this);

        ButterKnife.bind(this);

        setClick();
        onAttachView();
    }

    private void setClick() {

    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }

    @OnClick(R.id.btnDetail)
    void showImageFile(){
        //check extensi file
        Intent intent = new Intent(this, FileViewerActivity.class);
        intent.putExtra(EXTRA_URL, dataApproval.getFileData());
        intent.putExtra(EXTRA_PUM_TRX_ID, dataApproval.getPumTrxId());
        startActivity(intent);
    }

    @OnClick(R.id.btnApprove)
    void approve(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.approve));
        alert.setMessage(getString(R.string.message_dialog_approve_single));
        alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
            Intent intent = new Intent(this, PinActivity.class);
            startActivityForResult(intent, REQUEST_CODE_PIN);
            requestType = 1;
        }));
        alert.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
        alert.show();
    }


    @OnClick(R.id.btnReject)
    void reject(){
            FrameLayout container = new FrameLayout(Objects.requireNonNull(this));

            EditText editText = generateEditText();
            container.addView(editText);

            final AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(this));
            alert.setTitle(getString(R.string.reject));
            alert.setMessage(getString(R.string.message_dialog_reject_single));
            alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
                reasonValidation = editText.getText().toString();
                if(reasonValidation.isEmpty()){
                    Toast.makeText(this,"Reason validation can't empty", Toast.LENGTH_SHORT).show();
                    reject();
                }else {
                    Intent intent = new Intent(this, PinActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PIN);
                    requestType = 0;
                }
            }));
            alert.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
            alert.setView(container);
            alert.show();
    }

    private EditText generateEditText()
    {
        int padding = getResources().getDimensionPixelSize(R.dimen.min_margin);
        int margin = getResources().getDimensionPixelSize(R.dimen.main_margin);
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = margin;
        params.rightMargin = margin;

        final EditText input = new EditText(this);
        input.setLayoutParams(params);
        input.setBackground(getDrawable(R.drawable.bg_rounded_border_black_solid_black));
        input.setHint(getString(R.string.hint_dialog_reject));
        input.setMinLines(3);
        input.setGravity(Gravity.TOP);
        input.setPadding(padding, padding, padding, padding);
        return input;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PIN){
            if (resultCode == Activity.RESULT_OK){
                if (data != null) {
                    String pin = data.getStringExtra(PinActivity.EXTRA_PIN);
                    if(requestType==0){ //reject
                        presenter.reject(pumTrxId, pin, reasonValidation);
                    }else {
                        presenter.approve(pumTrxId, pin);
                    }
                }
            }
        }
    }

    @Override
    public void showData(DataApproval dataApproval) {
        this.dataApproval = dataApproval;
        tvPumNumber.setText(dataApproval.getTrxNum());
        tvPumRequester.setText(dataApproval.getName());
        tvDepartment.setText(dataApproval.getDepartment());
        tvTrxDate.setText(dataApproval.getTrxDate());
        tvUseDate.setText(dataApproval.getUseDate());
        tvTransactionType.setText(dataApproval.getPumTrxTypeId());
        tvDescription.setText(dataApproval.getDescription());
        tvAmount.setText(String.format("%s %s", getString(R.string.rp), Global.priceFormater(String.valueOf(dataApproval.getAmount()))));
        tvFileUploaded.setText(dataApproval.getUploadData());
        scrollView.setVisibility(View.VISIBLE);
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
    public void success(int requestType) {
        Intent intent = new Intent(this, ApprovalSuccessActivity.class);
        intent.putExtra(Extra.EXTRA_APPROVAL_HISTORY_TYPE,requestType);
        startActivity(intent);
        finish();
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
