package com.hudipo.pum_indomaret.features.response.subfeatures.responsefull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.fullresponseadded.FullResponseAddedActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsecartfull.ResponseCartFullActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsecartpartial.ResponseCartPartialActivity;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.Key;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseFullActivity extends AppCompatActivity implements CustomSpinnerFragment.SpinnerListener{

    @BindView(R.id.btnTransactionType)
    Button btnTransactionType;

    private ArrayList<OptionItem> optionUploadFiles = new ArrayList<>();
    private ArrayList<OptionItem> optionTransactionType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_full);
        ButterKnife.bind(this);

        addOptionItem();
    }

    private void addOptionItem() {
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_CAMERA, R.drawable.camera, "Camera", "camera"));
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_GALLERY, R.drawable.gallery, "Gallery", "gallery"));
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_FILES, R.drawable.files, "Files", "files"));

        optionTransactionType.add(new OptionItem(1, null, "Transaction Type 1", "Transaction Type 1"));
        optionTransactionType.add(new OptionItem(1, null, "Transaction Type 2", "Transaction Type 2"));
        optionTransactionType.add(new OptionItem(1, null, "Transaction Type 3", "Transaction Type 3"));
        optionTransactionType.add(new OptionItem(1, null, "Transaction Type 4", "Transaction Type 4"));
        optionTransactionType.add(new OptionItem(1, null, "Transaction Type 5", "Transaction Type 5"));
    }

    @OnClick(R.id.btnAdd)
    void btnAdd(){
        startActivity(new Intent(this, FullResponseAddedActivity.class));
    }

    @OnClick(R.id.btnFolder)
    void btnFolder(){
        startActivity(new Intent(this, ResponseCartFullActivity.class));
        Animatoo.animateZoom(this);
    }

    @OnClick(R.id.btnTransactionType)
    void btnTransactionType(){
        Global.openPicker(getSupportFragmentManager(), optionTransactionType, RequestCode.CODE_OPTION_TRANSACTION_TYPE, "Transaction Type");
    }

    @OnClick(R.id.btnUploadFile)
    void btnUploadFile(){
        Global.openPicker(getSupportFragmentManager(), optionUploadFiles, RequestCode.CODE_OPTION_UPLOAD_FILE, "Choose file from");
    }

    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideDown(this); //fire the slide left animation
        finish();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
       onBackPressed();
    }

    @Override
    public void onOptionItemSelected(OptionItem optionItem, int requestCode) {
        if (requestCode == RequestCode.CODE_OPTION_UPLOAD_FILE){
            Toast.makeText(this, "option : "+optionItem.getValue(), Toast.LENGTH_SHORT).show();
        }else if (requestCode == RequestCode.CODE_OPTION_TRANSACTION_TYPE){
            btnTransactionType.setText(optionItem.getValue().toString());
        }
    }
}
