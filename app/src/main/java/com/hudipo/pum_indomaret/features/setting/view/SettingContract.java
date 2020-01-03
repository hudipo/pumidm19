package com.hudipo.pum_indomaret.features.setting.view;

import android.net.Uri;

import com.hudipo.pum_indomaret.model.login.User;
import com.hudipo.pum_indomaret.view.MainView;

public class SettingContract {

    public interface SettingPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void uploadImage(Uri uri);
        void logout();
    }

    public interface SettingView extends MainView{
        void showData(User userData);
        void showLoading();
        void dismissLoading();
        void toast(String message);
        void finishActivity();
    }
}
