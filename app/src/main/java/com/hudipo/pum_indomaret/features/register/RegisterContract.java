package com.hudipo.pum_indomaret.features.register;

import com.hudipo.pum_indomaret.view.MainView;

public class RegisterContract {

    public interface RegisterPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void registerToServer(String empNum, String password, String pin);
    }

    public interface RegisterView extends MainView{
        void showLoading();
        void hideLoading();
        void failedRegister(String message);
        void registerSuccess();
        void errorServer();
    }
}
