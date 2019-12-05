package com.hudipo.pum_indomaret.features.setting.view;

import android.net.Uri;

import com.hudipo.pum_indomaret.model.login.User;
import com.hudipo.pum_indomaret.view.MainView;

public class ChangePinContract {

    public interface ChangePinPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void changePin(String currentPin, String newPin);
    }

    public interface ChangePinView extends MainView{
        void showLoading();
        void dismissLoading();
        void toast(String message);
        void showSuccess();
    }
}
