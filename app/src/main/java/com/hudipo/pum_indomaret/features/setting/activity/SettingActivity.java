package com.hudipo.pum_indomaret.features.setting.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.Key;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.StartActivity;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity implements CustomSpinnerFragment.SpinnerListener {

    private ArrayList<OptionItem> optionUploadImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        addOptionItem();
    }

    private void addOptionItem() {
        optionUploadImages.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_CAMERA, R.drawable.camera, "Camera", "camera"));
        optionUploadImages.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_GALLERY, R.drawable.gallery, "Gallery", "gallery"));
    }

    @OnClick(R.id.btnChangePhoto)
    void changePhoto(){
        Global.openPicker(getSupportFragmentManager(), optionUploadImages, RequestCode.CODE_OPTION_UPLOAD_IMAGE, "Choose file from");
    }

    @OnClick(R.id.btnChangePin)
    void changePin(){
        StartActivity.goTo(this, ChangePinActivity.class);
    }

    @Override
    public void onOptionItemSelected(OptionItem optionItem, int requestCode) {
        // TODO: 16/09/19 upload image
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }
}
