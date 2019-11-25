package com.hudipo.pum_indomaret.features.requestpum.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.searchtrx.SearchTrxActivity;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.model.trxtype.TrxItem;
import com.hudipo.pum_indomaret.repository.Repository;
import com.hudipo.pum_indomaret.utils.CheckPermission;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.Key;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.Utils;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;

import java.io.File;

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
    private Boolean isValid = false;
    public static final String EXTRA_DOCUMENT_DETAIL = "extra_document_detail";
    private RequestModel requestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_fund);
        ButterKnife.bind(this);

        getDataIntent();
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
                            requestModel.setFileDataUri(Utils.getRealPathImageFromURI(getApplicationContext(), tempUri));

                            File file = new  File(realPath);
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
                        }
                    }
                }
                break;
            case REQUEST_CODE_GALLERY:
                if (data != null){
                    Uri uriSelectedImage = data.getData();
                    if (uriSelectedImage != null){
                        String realPath = Utils.getRealPathImageFromURI(this, uriSelectedImage);
                        requestModel.setFileDataUri(realPath);

                        File file = new  File(realPath);
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
                        requestModel.setFileDataUri(realPath);
                        File file = new  File(realPath);
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

    private void getDataIntent() {
        if (getIntent() != null){
            requestModel = getIntent().getParcelableExtra(EXTRA_DOCUMENT_DETAIL);
        }
    }

    @OnClick(R.id.btnBackFund)
    void btnBackFund(){
        finish();
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
                if (CheckPermission.checkPermissionStorage(this, REQUEST_CODE_FILES)){
                    showDocument();
                }
            }
        }
    }

    private void showDocument() {
        String[] mimeTypes = {"application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/*");
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


