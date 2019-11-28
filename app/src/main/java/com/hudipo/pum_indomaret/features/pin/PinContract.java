package com.hudipo.pum_indomaret.features.pin;

import com.hudipo.pum_indomaret.view.MainView;

public interface PinContract {

    interface PinView extends MainView{
        void showProgress();
        void hideProgress();
        void showFailed();
        void showSuccess(String pin);
    }

    interface PinPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void clearComposite();
        void checkPinToServer(int empId, String pin);
    }
}
