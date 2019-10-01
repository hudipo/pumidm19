package com.hudipo.pum_indomaret.features.login;

import com.hudipo.pum_indomaret.view.MainView;

public class LoginContract {

    public interface LoginPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void setLogin(String NIK, String password);
    }

    public interface LoginView extends MainView{
        void errorNIK(String message);
        void errorPassword(String message);
        void failedLogin(String message);
        void showLoading();
        void dismissLoading();
        void loginSuccess();
        void errorServer();
    }

}
