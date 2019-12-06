package com.hudipo.pum_indomaret.features.setting.presenter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.setting.view.ChangePinContract;
import com.hudipo.pum_indomaret.features.setting.view.SettingContract;
import com.hudipo.pum_indomaret.model.login.User;
import com.hudipo.pum_indomaret.model.setting.ChangePinResponse;
import com.hudipo.pum_indomaret.model.setting.UploadProfilePicResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.networking.RetrofitClient;
import com.hudipo.pum_indomaret.utils.HawkStorage;

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

public class ChangePinPresenter implements ChangePinContract.ChangePinPresenterView<ChangePinContract.ChangePinView> {
    private Context context;
    private ChangePinContract.ChangePinView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;

    public ChangePinPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(ChangePinContract.ChangePinView view) {
        this.mView = view;
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.clear();
    }

    @Override
    public void changePin(String currentPin, String newPin) {
        mView.showLoading();
        composite.add(new ApiServices().getApiPumServices()
                .changePin(hawkStorage.getUserData().getEmpId(), currentPin, newPin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mView.dismissLoading();
                    if (response.isSuccessful()){
                        ChangePinResponse uploadProfilePicResponse = response.body();
                        if (uploadProfilePicResponse != null) {
                            if(!uploadProfilePicResponse.isError()){
                                mView.showSuccess();
                            }else {
                                mView.toast(uploadProfilePicResponse.getMessage());
                            }
                        }else {
                            mView.toast(context.getString(R.string.something_wrong));
                        }
                    }else {
                        Converter<ResponseBody, ChangePinResponse> errorConverter =
                                RetrofitClient.client().responseBodyConverter(ChangePinResponse.class, new Annotation[0]);
                        ChangePinResponse errorResponse;
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
