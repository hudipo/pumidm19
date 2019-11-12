package com.hudipo.pum_indomaret.features.requestpum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
import com.hudipo.pum_indomaret.features.requestpum.model.RequestInteractionImpl;
import com.hudipo.pum_indomaret.features.requestpum.presenter.FundPresenterImpl;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.model.trxtype.TrxItem;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.Key;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FundReqActivity extends AppCompatActivity implements RequestContract.FundView, CustomSpinnerFragment.SpinnerListener {

    @BindView(R.id.spnFundTrxType)
    Spinner spnTrxType;
    @BindView(R.id.edtFundDesc)
    EditText edtFundDesc;
    @BindView(R.id.edtFundAmount)
    EditText edtFundAmount;
    @BindView(R.id.tvSelectAFile)
    TextView tvSelectAFile;

    private ArrayList<OptionItem> optionUploadFiles = new ArrayList<>();

    private FundPresenterImpl presenter;
    private RequestModel requestModel;
    public static String KEY_DATA_REQUEST_DOCUMENT = "KEY_DATA_REQUEST_DOCUMENT";
    private HawkStorage hawkStorage;
    private Boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_fund);
        ButterKnife.bind(this);

        initHawkStorage();
        initPresenter();
        addOptionItem();
        getDataIntentFromDocumentActivity();
    }

    private void addOptionItem() {
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_CAMERA, R.drawable.camera, "Camera", "camera"));
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_GALLERY, R.drawable.gallery, "Gallery", "gallery"));
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_FILES, R.drawable.files, "Files", "files"));
    }

    private void initHawkStorage() {
        hawkStorage = new HawkStorage(this);
    }

    private void initPresenter() {
        presenter = new FundPresenterImpl(this,new RequestInteractionImpl(this));
        presenter.getTransactionTypeList();
    }

    private void getDataIntentFromDocumentActivity() {
        if (getIntent()!=null){
            requestModel = (RequestModel) getIntent().getSerializableExtra(KEY_DATA_REQUEST_DOCUMENT);
        }
    }

    @OnClick(R.id.btnNextFund)
    void onClick(){
        checkValidData();
        goToValidationActivity();
    }

    private void goToValidationActivity() {
        if (isValid){
            String trxTypeDesc = spnTrxType.getSelectedItem().toString();
            String description = edtFundDesc.getText().toString();
            String amount = edtFundAmount.getText().toString();
            String uploadFile = tvSelectAFile.getText().toString();

            for (TrxItem trxItem: hawkStorage.getTrxTypeData().getTrx()){
                if (trxItem.getDescription().equals(trxTypeDesc)){
                    requestModel.setStringTrxType(trxItem.getDescription());
                    requestModel.setIdTrxType(trxItem.getPumTrxTypeId());
                }
            }

            requestModel.setStringDescription(description);
            requestModel.setStringFileUri(uploadFile);
            requestModel.setIntAmount(Integer.parseInt(amount));
            requestModel.setStringTrxType(trxTypeDesc);

            if (requestModel != null){
                Intent intent = new Intent(FundReqActivity.this, ValidationReqActivity.class);
                intent.putExtra(ValidationReqActivity.KEY_DATA_REQUEST, requestModel);
                startActivity(intent);
            }
        }
    }

    private void checkValidData() {
        if (edtFundDesc.getText().toString().isEmpty()){
            edtFundDesc.setError("Description cannot be empty");
            isValid = false;
        }else {
            edtFundDesc.setError(null);
            isValid = true;
        }

        if (edtFundAmount.getText().toString().isEmpty()){
            edtFundAmount.setError("Amount cannot be empty");
            isValid = false;
        }else {
            edtFundAmount.setError(null);
            isValid = true;
        }

        if (tvSelectAFile.getText().toString().isEmpty()){
            tvSelectAFile.setError("File cannot be empty");
            Toast.makeText(this, "File cannot be empty", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else {
            tvSelectAFile.setError(null);
            isValid = true;
        }
    }

    @OnClick(R.id.imgUploadFile)
    void onImgUploadFile(){
        Global.openPicker(getSupportFragmentManager(), optionUploadFiles, RequestCode.CODE_OPTION_UPLOAD_FILE, "Choose file from");
    }

    @OnClick(R.id.imgBack)
    void onBackClick(){
        super.onBackPressed();
    }

    @Override
    public void setTransactionType(ArrayList<String> transactionTypeList) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, transactionTypeList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnTrxType.setAdapter(dataAdapter);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onOptionItemSelected(OptionItem optionItem, int requestCode) {
        if (requestCode == RequestCode.CODE_OPTION_UPLOAD_FILE){
            tvSelectAFile.setText(optionItem.getLabel());
        }
    }
}
