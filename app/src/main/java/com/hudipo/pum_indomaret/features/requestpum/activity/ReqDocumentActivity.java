package com.hudipo.pum_indomaret.features.requestpum.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.searchdocdetail.SearchDocDetailActivity;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.model.docdetail.DocDataItem;
import com.hudipo.pum_indomaret.repository.Repository;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReqDocumentActivity extends AppCompatActivity {

    @BindView(R.id.spnDocType)
    Spinner spnDocType;
    @BindView(R.id.tvDocNum)
    TextView tvDocNum;
    @BindView(R.id.btnSearchDoc)
    ImageButton btnSearchDoc;

    private static final int REQ_CODE_DOC_DETAIL = 100;

    private HawkStorage hawkStorage;
    private RequestModel requestModel;
    private String docNum = "";
    private String nameDocType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_document);
        ButterKnife.bind(this);

        initSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHawkStorage();
        checkSpinnerType();
        setDataView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!docNum.isEmpty() && !nameDocType.isEmpty()){
            requestModel.setDocNum(docNum);
            requestModel.setNameDocType(nameDocType);
            hawkStorage.setRequestModel(requestModel);
        }
        Animatoo.animateSlideRight(this);
    }

    private void setDataView() {
        if (hawkStorage.getRequestModel() != null){
            Log.d("coba", "data: "+new Gson().toJson(hawkStorage.getRequestModel()));
            if (hawkStorage.getRequestModel().getDocNum() != null && !hawkStorage.getRequestModel().getDocNum().isEmpty()){
                tvDocNum.setText(hawkStorage.getRequestModel().getDocNum());
            }

            if (requestModel.getPositionDocType() > -1){
                spnDocType.setSelection(requestModel.getPositionDocType());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_DOC_DETAIL){
            if (resultCode == Activity.RESULT_OK){
                if (data != null) {
                    DocDataItem docDataItem = data.getParcelableExtra(SearchDocDetailActivity.EXTRA_SELECTED_DOC);
                    if (docDataItem != null) {
                        docNum = docDataItem.getDocNum();
                        tvDocNum.setText(docNum);
                        requestModel.setDocNum(docNum);

                        hawkStorage.setRequestModel(requestModel);
                    }
                }
            }
        }
    }

    private void disableButtonSearch(){
        btnSearchDoc.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_red));
        btnSearchDoc.setEnabled(false);
    }

    private void enableButtonSearch(){
        btnSearchDoc.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_green));
        btnSearchDoc.setEnabled(true);
    }

    private void checkSpinnerType() {
        spnDocType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameDocType = spnDocType.getSelectedItem().toString().trim();

                if (nameDocType.equals("-") || nameDocType.equals(getString(R.string.doc_type))){
                    nameDocType = "-";
                    requestModel.setNameDocType(nameDocType);
                    requestModel.setDocNum(nameDocType);
                    requestModel.setPositionDocType(position);

                    hawkStorage.setRequestModel(requestModel);
                    tvDocNum.setText(nameDocType);
                    disableButtonSearch();
                }else {
                    requestModel.setPositionDocType(position);
                    requestModel.setNameDocType(nameDocType);

                    hawkStorage.setRequestModel(requestModel);
                    enableButtonSearch();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                nameDocType = spnDocType.getSelectedItem().toString().trim();
            }
        });
    }

    private void initSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, Repository.getDataDocumentType(this));
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnDocType.setAdapter(dataAdapter);
    }

    private void initHawkStorage() {
        hawkStorage = new HawkStorage(this);
        if (hawkStorage.getRequestModel()!= null){
            requestModel = hawkStorage.getRequestModel();
        }
    }

    @OnClick(R.id.btnSearchDoc)
    void setBtnSearchDoc(){
        Intent intent = new Intent(this, SearchDocDetailActivity.class);
        intent.putExtra(SearchDocDetailActivity.KEY_DATA_DOC_TYPE, spnDocType.getSelectedItem().toString());
        startActivityForResult(intent, REQ_CODE_DOC_DETAIL);
    }


    @OnClick(R.id.btnBackDoc)
    void btnBackDoc(){
        onBackPressed();
    }

    @OnClick(R.id.btnNextDoc)
    void btnNextDoc(){
        String docNum = tvDocNum.getText().toString();
        String docSelected = spnDocType.getSelectedItem().toString().trim();
        if (checkValid(docNum, docSelected)){
            Intent intent = new Intent(this, ReqFundActivity.class);
            startActivity(intent);
            Animatoo.animateSlideLeft(this);

        }
    }

    private boolean checkValid(String docNum, String docSelected) {
        if (docSelected.equals("-") || docSelected.equals(getString(R.string.doc_type))){
            return true;
        }else {
            if (docNum.isEmpty()){
                tvDocNum.setError("Your document is empty");
                Toast.makeText(this, "Your document is empty", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                tvDocNum.setError(null);
            }
        }

        return true;
    }
}
