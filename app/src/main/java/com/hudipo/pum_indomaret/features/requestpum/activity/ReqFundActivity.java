package com.hudipo.pum_indomaret.features.requestpum.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.searchtrx.SearchTrxActivity;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.model.trxtype.TrxItem;
import com.hudipo.pum_indomaret.repository.Repository;
import com.hudipo.pum_indomaret.utils.CheckPermission;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.Key;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.Utils;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReqFundActivity extends AppCompatActivity implements CustomSpinnerFragment.SpinnerListener {

    @BindView(R.id.btnSearchTrx)
    Button btnSearchTrx;
    @BindView(R.id.etDescFund)
    EditText etDescFund;
    @BindView(R.id.etAmountFund)
    EditText etAmountFund;
    @BindView(R.id.tvSelectAFile)
    TextView tvSelectAFile;
    @BindView(R.id.tvErrorAmount)
    TextView tvErrorAmount;

    private final int REQUEST_CODE_SEARCH_TRX = 100;
    private final int REQUEST_CODE_CAMERA = 101;
    private final int REQUEST_CODE_GALLERY = 102;
    private final int REQUEST_CODE_FILES = 103;
    private RequestModel requestModel;
    HawkStorage hawkStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_fund);
        ButterKnife.bind(this);

        setCurrencyEtAmountFund();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setRequestModel();
        setDataView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String amount = etAmountFund.getText().toString().trim().replace(".", "");
        String desc = etDescFund.getText().toString().trim();
        String trx = btnSearchTrx.getText().toString().trim();
        String nameFile = tvSelectAFile.getText().toString().trim();

        if (checkDataNotNull(amount, desc, trx, nameFile)){
            requestModel.setDescription(desc);
            requestModel.setAmount(amount);
            requestModel.setNameTrxType(trx);
            requestModel.setNameFile(nameFile);

            hawkStorage.setRequestModel(requestModel);
        }
        Animatoo.animateSlideRight(this);
    }

    private boolean checkDataNotNull(String amount, String desc, String trx, String nameFile) {
        return !amount.isEmpty() && !desc.isEmpty() && !trx.isEmpty() && !nameFile.isEmpty();
    }

    private void setDataView() {
        if (hawkStorage.getRequestModel() != null){
            if (hawkStorage.getRequestModel().getNameTrxType() != null && !hawkStorage.getRequestModel().getNameTrxType().isEmpty()){
                btnSearchTrx.setText(requestModel.getNameTrxType());
            }

            if (requestModel.getDescription() != null && !requestModel.getDescription().isEmpty()){
                etDescFund.setText(requestModel.getDescription());
            }

            if (requestModel.getNameFile() != null && !requestModel.getNameFile().isEmpty()){
                tvSelectAFile.setText(requestModel.getNameFile());
            }

            if (requestModel.getAmount() != null && !requestModel.getAmount().isEmpty()) {
                etAmountFund.setText(requestModel.getAmount());
            }
        }
    }

    private void setRequestModel() {
        hawkStorage = new HawkStorage(this);
        if (hawkStorage.getRequestModel() != null){
            requestModel = hawkStorage.getRequestModel();
        }
    }

    private void setCurrencyEtAmountFund() {
        etAmountFund.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String cleanString = s.toString().replaceAll("[$,.]", "");

                if (!cleanString.isEmpty()){
                    etAmountFund.removeTextChangedListener(this);

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getInstance(new Locale("id")).format(parsed);

                    etAmountFund.setText(formatted);
                    etAmountFund.setSelection(formatted.length());

                    etAmountFund.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null){
                        Bitmap bitmap = (Bitmap) extras.get("data");
                        if (bitmap != null){
                            Uri tempUri = Utils.getImageUri(this, bitmap);
                            String realPath = Utils.getRealPathImageFromURI(this, tempUri);
                            File file = new  File(realPath);

                            requestModel.setFileDataUri(tempUri.toString());
                            requestModel.setImage(true);
                            requestModel.setNameFile(file.getName());

                            hawkStorage.setRequestModel(requestModel);

                            tvSelectAFile.setText(file.getName());
                        }else {
                            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case REQUEST_CODE_SEARCH_TRX:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        TrxItem trxItem = data.getParcelableExtra(SearchTrxActivity.EXTRA_SELECTED);
                        if (trxItem != null){
                            btnSearchTrx.setText(trxItem.getDescription());
                            requestModel.setTrxTypeId(trxItem.getPumTrxTypeId());
                            requestModel.setNameTrxType(trxItem.getDescription());

                            hawkStorage.setRequestModel(requestModel);
                        }
                    }
                }
                break;
            case REQUEST_CODE_GALLERY:
                if (data != null){
                    Uri uriSelectedImage = data.getData();
                    if (uriSelectedImage != null){
                        String realPath = Utils.getRealPathImageFromURI(this, uriSelectedImage);
                        File file = new  File(realPath);

                        requestModel.setFileDataUri(uriSelectedImage.toString());
                        requestModel.setImage(true);
                        requestModel.setNameFile(file.getName());

                        hawkStorage.setRequestModel(requestModel);

                        tvSelectAFile.setText(file.getName());
                    }else {
                        Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_FILES:
                if (data != null){
                    Uri selectedFile = data.getData();

                    if (selectedFile != null){
                        String realPath = Utils.getRealPathDocumentFromUri(this, selectedFile);
                        File file = new  File(realPath);

                        requestModel.setFileDataUri(selectedFile.toString());
                        requestModel.setImage(false);
                        requestModel.setNameFile(file.getName());

                        hawkStorage.setRequestModel(requestModel);

//                        String path = selectedFile.getPath();
//                        requestModel.setPathDocument(path);

                        tvSelectAFile.setText(file.getName());
                    }else {
                        Toast.makeText(this, "Failed to get files", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        showCamera();
                    }
                }else {
                    Toast.makeText(this, "Access camera is denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_GALLERY:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        showGallery();
                    }else {
                        Toast.makeText(this, "Access gallery is denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_FILES:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        showDocument();
                    }else {
                        Toast.makeText(this, "Access to document is denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @OnClick(R.id.btnBackFund)
    void btnBackFund(){
        onBackPressed();
    }

    @OnClick(R.id.btnSearchTrx)
    void btnSearchTrx(){
        Intent intent = new Intent(this, SearchTrxActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SEARCH_TRX);
    }

    @OnClick(R.id.btnUploadFile)
    void btnUploadFile(){
        Global.openPicker(getSupportFragmentManager(), Repository.getDataChooseFile(), RequestCode.CODE_OPTION_UPLOAD_FILE, "Choose file from");
    }

    @OnClick(R.id.btnNextFund)
    void btnNextFund(){
        String amount = etAmountFund.getText().toString().trim().replace(".", "");
        String desc = etDescFund.getText().toString().trim();
        String trx = btnSearchTrx.getText().toString().trim();
        String nameFile = tvSelectAFile.getText().toString().trim();

        if (checkValid(amount, desc, trx)){
            requestModel.setDescription(desc);
            requestModel.setAmount(amount);
            requestModel.setNameTrxType(trx);
            requestModel.setNameFile(nameFile);

            hawkStorage.setRequestModel(requestModel);

            Intent intent = new Intent(this, ReqValidationActivity.class);
            startActivity(intent);
        }
    }


    private Boolean checkValid(String amount, String desc, String trx) {
        if (trx.isEmpty()){
            Toast.makeText(this, "Trx is still empty", Toast.LENGTH_SHORT).show();
            btnSearchTrx.setError("Trx is still empty");
            return false;
        }else {
            btnSearchTrx.setError(null);
        }

        if (desc.isEmpty()){
            Toast.makeText(this, "Description is still empty", Toast.LENGTH_SHORT).show();
            etDescFund.setError("Description is still empty");
            return false;
        }else {
            etDescFund.setError(null);
        }

        if (amount.isEmpty()){
            Toast.makeText(this, "Amount is still empty", Toast.LENGTH_SHORT).show();
            etAmountFund.setError("Amount is still empty");
            return false;
        }else if(!checkAmountMax(amount)) {
                etAmountFund.setError(null);
                String textError = "Your input amount is over the limit";
                tvErrorAmount.setText(textError);
                tvErrorAmount.setVisibility(View.VISIBLE);
                return false;
        }else if(!checkAmountMin(amount)) {
            etAmountFund.setError(null);
            String textError = "Your input amount is too small ";
            tvErrorAmount.setText(textError);
            tvErrorAmount.setVisibility(View.VISIBLE);
            return false;
        }
        else {
                tvErrorAmount.setError(null);

        }


        return true;
    }

    private Boolean checkAmountMax(String amount) {
        boolean isValid = false;
        if (!amount.isEmpty()){
            Double totalAmount = Double.valueOf(amount);
            if (totalAmount > hawkStorage.getUserData().getMaxAmount()){

                isValid = false;
            }else {
                isValid = true;
                tvErrorAmount.setVisibility(View.INVISIBLE);
            }
        }
        return isValid;
    }

    private Boolean checkAmountMin(String amount) {
        boolean isValid = false;
        if (!amount.isEmpty()){
            Double totalAmount = Double.valueOf(amount);
            if (totalAmount < hawkStorage.getUserData().getMinAmount() ){

                isValid = false;
            }else {
                isValid = true;
                tvErrorAmount.setVisibility(View.INVISIBLE);
            }
        }
        return isValid;
    }

//    private void showErrorAmountDialog() {
//        new AlertDialog.Builder(this, R.style.CustomDialogTheme)
//                .setTitle("Sorry...")
//                .setMessage("Your amount is over the limit, Please contact Finance division")
//                .setCancelable(true)
//                .show();
//    }
//
//    private void showErrorMinAmountDialog() {
//        new AlertDialog.Builder(this, R.style.CustomDialogTheme)
//                .setTitle("Sorry...")
//                .setMessage("Your amount is under the limit")
//                .setCancelable(true)
//                .show();
//    }

    @Override
    public void onOptionItemSelected(OptionItem optionItem, int requestCode) {
        if (requestCode == RequestCode.CODE_OPTION_UPLOAD_FILE){
            if (optionItem.getKey() == Key.KEY_UPLOAD_FILE_WITH_CAMERA){
                if (CheckPermission.checkPermissionCamera(this, REQUEST_CODE_CAMERA)){
                    showCamera();
                }
            }else if (optionItem.getKey() == Key.KEY_UPLOAD_FILE_WITH_GALLERY){
                if (CheckPermission.checkPermissionStorage(this, REQUEST_CODE_GALLERY)){
                    showGallery();
                }
            }else if (optionItem.getKey() == Key.KEY_UPLOAD_FILE_WITH_FILES){
//                if (CheckPermission.checkPermissionStorage(this, REQUEST_CODE_FILES)){
//                    showDocument();
//                }
                Toast.makeText(this, "This feature is not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDocument() {
        String[] mimeTypes = {"application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.setType("application/msword");
        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.documen");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, REQUEST_CODE_FILES);
    }

    private void showGallery() {
        Intent intentPickImage = new Intent(Intent.ACTION_PICK);
        intentPickImage.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
        intentPickImage.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intentPickImage, REQUEST_CODE_GALLERY);
    }

    private void showCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }


}


