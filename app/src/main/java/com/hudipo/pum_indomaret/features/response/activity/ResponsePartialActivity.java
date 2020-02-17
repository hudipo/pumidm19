package com.hudipo.pum_indomaret.features.response.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.db.CartResponsePartialHelper;
import com.hudipo.pum_indomaret.db.DatabaseContract;
import com.hudipo.pum_indomaret.features.searchstorecode.SearchStoreCodeActivity;
import com.hudipo.pum_indomaret.features.searchtrxtyperesponse.SearchTrxTypeResponseActivity;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.model.response.TrxTypeItem;
import com.hudipo.pum_indomaret.model.storecode.StoreCodeItem;
import com.hudipo.pum_indomaret.model.submitresponse.SubmitResponseItem;
import com.hudipo.pum_indomaret.repository.Repository;
import com.hudipo.pum_indomaret.utils.CheckPermission;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.Key;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.Utils;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponsePartialActivity extends AppCompatActivity implements CustomSpinnerFragment.SpinnerListener{

    static public String EXTRA_TRX_TYPE = "extra_trx_type";
    static public String EXTRA_TRX_NUMBER = "extra_trx_number";
    static public String EXTRA_PUM_TRX_ID = "extra_pum_trx_id";

    @BindView(R.id.btnTransactionType)
    Button btnTransactionType;
    @BindView(R.id.etDescription)
    EditText etDescription;
    @BindView(R.id.etAmount)
    EditText etAmount;
    @BindView(R.id.etUploadFile)
    EditText etUploadFile;
    @BindView(R.id.btnStoreCode)
    Button btnStoreCode;

    private final int REQUEST_CODE_SEARCH_TRX = 100;
    private final int REQUEST_CODE_CAMERA = 101;
    private final int REQUEST_CODE_GALLERY = 102;
    private final int REQUEST_CODE_FILES = 103;
    private final int REQUEST_CODE_SEARCH_STORE_CODE = 104;

    private ArrayList<TrxTypeItem>  trxTypeItems;
    private String trxNumber;
    private int pumTrxId;
    private TrxTypeItem trxTypeItem;
    private StoreCodeItem storeCodeItem;
    private SubmitResponseItem submitResponseItem = new SubmitResponseItem();
    private CartResponsePartialHelper helper = CartResponsePartialHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_partial);
        ButterKnife.bind(this);

        openHelperDB();
        getDataIntent();
        setCurrencyEtAmountFund();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SEARCH_TRX:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null) {
                        trxTypeItem = data.getParcelableExtra(SearchTrxTypeResponseActivity.EXTRA_SELECTED_TRX_TYPE);
                        if (trxTypeItem != null){
                            setBtnTransactionType(trxTypeItem);
                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMERA:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null){
                        Bitmap bitmap = (Bitmap) extras.get("data");
                        if (bitmap != null){
                            Uri tempUri = Utils.getImageUri(this, bitmap);
                            String typeFile = getContentResolver().getType(tempUri);
                            String realPath = Utils.getRealPathImageFromURI(this, tempUri);

                            submitResponseItem.setRealPath(realPath);
                            submitResponseItem.setTypeFile(typeFile);

                            File file = new  File(realPath);
                            etUploadFile.setText(file.getName());
                        }else {
                            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case REQUEST_CODE_GALLERY:
                if (data != null){
                    Uri uriSelectedImage = data.getData();
                    if (uriSelectedImage != null){
                        String realPath = Utils.getRealPathImageFromURI(this, uriSelectedImage);
                        String typeFile = getContentResolver().getType(uriSelectedImage);

                        submitResponseItem.setRealPath(realPath);
                        submitResponseItem.setTypeFile(typeFile);

                        File file = new  File(realPath);
                        etUploadFile.setText(file.getName());
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
                        etUploadFile.setText(file.getName());
                        etUploadFile.setText(file.getAbsolutePath());
                    }else {
                        Toast.makeText(this, "Failed to get files", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_SEARCH_STORE_CODE:
                if (data != null){
                    storeCodeItem = data.getParcelableExtra(SearchStoreCodeActivity.EXTRA_SELECTED);
                    if (storeCodeItem != null){
                        btnStoreCode.setText(storeCodeItem.getSTORENAME());
                    }
                }
                break;
        }
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
//                if (CheckPermission.checkPermissionStorage(this, REQUEST_CODE_FILES)){
//                    showDocument();
//                }
                Toast.makeText(this, "This feature is not available", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == RequestCode.CODE_OPTION_TRANSACTION_TYPE){
            btnTransactionType.setText(optionItem.getValue().toString());
        }
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }

    @OnClick(R.id.btnTransactionType)
    void btnTransactionType(){
        Intent intent = new Intent(this, SearchTrxTypeResponseActivity.class);
        intent.putParcelableArrayListExtra(SearchTrxTypeResponseActivity.EXTRA_TRX_TYPE, trxTypeItems);
        startActivityForResult(intent, REQUEST_CODE_SEARCH_TRX);
    }

    @OnClick(R.id.btnUploadFile)
    void btnUploadFile(){
        Global.openPicker(getSupportFragmentManager(), Repository.getDataChooseFileRes(), RequestCode.CODE_OPTION_UPLOAD_FILE, "Choose file from");
    }

    @OnClick(R.id.btnAdd)
    void btnAdd(){
        String transactionType = btnTransactionType.getText().toString();
        String description = etDescription.getText().toString();
        String amount = etAmount.getText().toString().trim().replace(".", "");
        String uploadFile = etUploadFile.getText().toString();
        String storeCode = btnStoreCode.getText().toString();

        if (checkValid(transactionType, description, amount, uploadFile, storeCode)){
            submitResponseItem.setTrxTypeId(trxTypeItem.getPUMRESPTRXTYPEID());
            submitResponseItem.setTextTransactionType(trxTypeItem.getNAME());
            submitResponseItem.setAmount(amount);
            submitResponseItem.setDescription(description);
            submitResponseItem.setAmount(amount);
            submitResponseItem.setStoreCode(storeCodeItem.getSTORECODE());

            inputDataToDatabase(submitResponseItem);
        }
    }

    @OnClick(R.id.btnCartResponseFull)
    void btnFolder(){
        Intent intent = new Intent(this, ResponseCartPartialActivity.class);
        intent.putExtra(ResponseCartPartialActivity.EXTRA_TRX_NUMBER, trxNumber);
        intent.putExtra(ResponseCartPartialActivity.EXTRA_PUM_TRX_ID, pumTrxId);
        startActivity(intent);
    }

    @OnClick(R.id.btnStoreCode)
    void btnStoreCode(){
        startActivityForResult(new Intent(this, SearchStoreCodeActivity.class), REQUEST_CODE_SEARCH_STORE_CODE);
    }

    private void openHelperDB() {
        helper.open();
    }

    private void setCurrencyEtAmountFund() {
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String cleanString = s.toString().replaceAll("[$,.]", "");

                if (!cleanString.isEmpty()){
                    etAmount.removeTextChangedListener(this);

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getInstance(new Locale("id")).format(parsed);

                    etAmount.setText(formatted);
                    etAmount.setSelection(formatted.length());

                    etAmount.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setBtnTransactionType(TrxTypeItem trxTypeItem) {
        btnTransactionType.setText(trxTypeItem.getNAME());
    }

    private void getDataIntent() {
        if (getIntent() != null){
            trxTypeItems = getIntent().getParcelableArrayListExtra(EXTRA_TRX_TYPE);
            trxNumber = getIntent().getStringExtra(EXTRA_TRX_NUMBER);
            pumTrxId = getIntent().getIntExtra(EXTRA_PUM_TRX_ID, 0);
        }
    }

    private void inputDataToDatabase(SubmitResponseItem submitResponseItem) {
        String submitResponseString = new Gson().toJson(submitResponseItem);
        ContentValues values = new ContentValues();
        if (trxNumber != null){
            values.put(DatabaseContract.CartResponsePartialColumns.TRX_NUMBER_PARTIAL, trxNumber);
        }
        values.put(DatabaseContract.CartResponsePartialColumns.SUBMIT_RESPONSE_ITEM_PARTIAL, submitResponseString);

        Long result = helper.insert(values);
        if (result > 0){
            btnTransactionType.setText(null);
            etDescription.setText(null);
            etAmount.setText(null);
            etUploadFile.setText(null);
            btnStoreCode.setText(null);

            Intent intent = new Intent(this, PartialResponseAddedActivity.class);
            intent.putExtra(PartialResponseAddedActivity.EXTRA_TRX_NUMBER, trxNumber);
            intent.putExtra(PartialResponseAddedActivity.EXTRA_PUM_TRX_ID, pumTrxId);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Failed add to cart", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkValid(String transactionType, String description, String amount, String uploadFile, String storeCode) {
        if (transactionType.isEmpty()){
            btnTransactionType.setError(getString(R.string.you_have_not_selected_transaction_type));
            Toast.makeText(this, getString(R.string.you_have_not_selected_transaction_type), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            btnTransactionType.setError(null);
        }

        if (description.isEmpty()){
            etDescription.setError(getString(R.string.your_desc_is_empty));
            Toast.makeText(this, getString(R.string.your_desc_is_empty), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            etDescription.setError(null);
        }

        if (amount.isEmpty()){
            etAmount.setError(getString(R.string.your_amount_is_empty));
            Toast.makeText(this, getString(R.string.your_amount_is_empty), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            etAmount.setError(null);
        }

        if (uploadFile.isEmpty()){
            etUploadFile.setError(getString(R.string.your_file_is_empty));
            Toast.makeText(this, getString(R.string.your_file_is_empty), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            etUploadFile.setError(null);
        }

        if (storeCode.isEmpty()){
            btnStoreCode.setError(getString(R.string.you_have_not_selected_store_code));
            Toast.makeText(this, getString(R.string.you_have_not_selected_store_code), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            btnStoreCode.setError(null);
        }
        return true;
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
}
