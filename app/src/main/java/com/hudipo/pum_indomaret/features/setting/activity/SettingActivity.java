package com.hudipo.pum_indomaret.features.setting.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.login.LoginActivity;
import com.hudipo.pum_indomaret.features.setting.presenter.SettingPresenter;
import com.hudipo.pum_indomaret.features.setting.view.SettingContract;
import com.hudipo.pum_indomaret.helper.CustomLoadingProgress;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.model.login.User;
import com.hudipo.pum_indomaret.utils.CheckPermission;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.Key;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.StartActivity;
import com.hudipo.pum_indomaret.utils.Utils;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity implements CustomSpinnerFragment.SpinnerListener, SettingContract.SettingView {
    @BindView(R.id.ciPhotoProfile)
    CircleImageView ciPhotoProfile;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmpNum)
    TextView tvEmpNum;

    @BindView(R.id.tvPosition)
    TextView tvPosition;

    @BindView(R.id.tvRespName)
    TextView tvRespName;

//    @BindView(R.id.loading)
//    LottieAnimationView loading;

    private static final String TAG = "SettingActivity";
    private ArrayList<OptionItem> optionUploadImages = new ArrayList<>();
    private HawkStorage hawkStorage;
    private final int REQUEST_CODE_CAMERA = 101;
    private final int REQUEST_CODE_GALLERY = 102;
    private SettingPresenter presenter;
    private CustomLoadingProgress loadingProgress = new CustomLoadingProgress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        presenter = new SettingPresenter(this);

        initHawkStorage();
        addOptionItem();

        onAttachView();
        showData(hawkStorage.getUserData());
    }

    private void initHawkStorage() {
        hawkStorage = new HawkStorage(this);
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

    @OnClick(R.id.btnLogout)
    void logout(){
        presenter.logout();
    }

    @Override
    public void onOptionItemSelected(OptionItem optionItem, int requestCode) {
        if (requestCode == RequestCode.CODE_OPTION_UPLOAD_IMAGE){
            if (optionItem.getKey() == Key.KEY_UPLOAD_FILE_WITH_CAMERA){
                if (CheckPermission.checkPermissionCamera(this, REQUEST_CODE_CAMERA)){
                    showCamera();
                }
            }else if (optionItem.getKey() == Key.KEY_UPLOAD_FILE_WITH_GALLERY){
                if (CheckPermission.checkPermissionStorage(this, REQUEST_CODE_GALLERY)){
                    showGallery();
                }
            }
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
        }
    }

    private void showGallery() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.GALLERY_ONLY)
                .single()
                .showCamera(false)
                .start();
    }

    private void showCamera() {
        ImagePicker.cameraOnly().start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            if(image!=null) runCrop(image.getPath());
        }else if(resultCode == RESULT_OK && requestCode==UCrop.REQUEST_CROP){
            if (data != null) {
                Uri uri = UCrop.getOutput(data);
                String path = Utils.getRealPathImageFromURI(SettingActivity.this, uri);
                presenter.uploadImage(UCrop.getOutput(data));
            }
        }
    }

    private void runCrop(String path){
        Uri uri = Uri.fromFile(new File(path));
        String fileName = System.currentTimeMillis()+".png";

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getString(R.string.app_name));
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            UCrop.of(uri, Uri.fromFile(new File(storageDir, fileName)))
                    .withAspectRatio(1, 1)
                    .start(this, UCrop.REQUEST_CROP);
        }else {
            Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }

    @Override
    public void showData(User userData) {
        if(userData!=null){
            Glide.with(this)
                    .load(userData.getPhotoProfile())
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(ciPhotoProfile);
            tvName.setText(userData.getName());
            tvPosition.setText(userData.getPosition());
            tvEmpNum.setText(userData.getEmpNum());
            tvRespName.setText(userData.getRespName());
            if (tvRespName.getText().toString().matches("SUPERUSER_MENU")){
                tvRespName.setText("USER");
            }else if(tvRespName.getText().toString().matches("APPROVAL_MENU")){
                tvRespName.setText("APPROVAL");
            }
        }
    }

    @Override
    public void showLoading() {
        loadingProgress.showCustomDialog(this);
    }

    @Override
    public void dismissLoading() {
        loadingProgress.closeCustomDialog();
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        StartActivity.goTo(this, LoginActivity.class);
        hawkStorage.deleteAll();
        finishAffinity();
    }

    @Override
    public void onAttachView() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }
}
