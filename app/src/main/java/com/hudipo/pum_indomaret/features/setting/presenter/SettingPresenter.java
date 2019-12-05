package com.hudipo.pum_indomaret.features.setting.presenter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.setting.view.SettingContract;
import com.hudipo.pum_indomaret.model.login.LoginResponse;
import com.hudipo.pum_indomaret.model.login.User;
import com.hudipo.pum_indomaret.model.setting.UploadProfilePicResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.networking.RetrofitClient;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class SettingPresenter implements SettingContract.SettingPresenterView<SettingContract.SettingView> {
    private Context context;
    private SettingContract.SettingView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;

    public SettingPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(SettingContract.SettingView view) {
        this.mView = view;
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.clear();
    }

    @Override
    public void uploadImage(Uri uri) {
        mView.showLoading();

        File fileImage = new File(Objects.requireNonNull(uri.getPath()));
        RequestBody image = RequestBody.create(fileImage, MediaType.parse("image/*"));
        MultipartBody.Part imageRequest = MultipartBody.Part.createFormData("image",
                fileImage.getName(), image);


        HashMap <String, RequestBody> params = new HashMap<>();
        RequestBody requestEmpId = RequestBody.create(String.valueOf(hawkStorage.getUserData().getEmpId()), MediaType.parse("text/plain"));
        params.put("emp_id", requestEmpId);

        composite.add(new ApiServices().getApiPumServices()
                .uploadProfilePic(params, imageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mView.dismissLoading();
                    if (response.isSuccessful()){
                        UploadProfilePicResponse uploadProfilePicResponse = response.body();
                        if (uploadProfilePicResponse != null) {
                            mView.toast(uploadProfilePicResponse.getMessage());
                            User user = hawkStorage.getUserData();
                            //todo minta responsenya ditambah
//                            user.setPhotoProfile();
                            mView.showData(user);
                        }else {
                            mView.toast(context.getString(R.string.something_wrong));
                        }
                    }else {
                        Converter<ResponseBody, UploadProfilePicResponse> errorConverter =
                                RetrofitClient.client().responseBodyConverter(UploadProfilePicResponse.class, new Annotation[0]);
                        UploadProfilePicResponse errorResponse;
                        try {
                            if (response.errorBody() != null){
                                errorResponse = errorConverter.convert(response.errorBody());

                                if (errorResponse != null){
                                    mView.toast(errorResponse.getMessage());
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                            mView.toast(context.getString(R.string.err_server));
                        }
                    }
                }, throwable -> {
                    Log.d(TAG, "uploadImage: "+throwable.getMessage());
                    mView.dismissLoading();
                    mView.toast(context.getString(R.string.err_server));
                }));
    }
}
