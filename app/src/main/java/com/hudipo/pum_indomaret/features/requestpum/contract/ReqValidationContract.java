package com.hudipo.pum_indomaret.features.requestpum.contract;

import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.view.MainView;

public interface ReqValidationContract {
    interface ReqValidationView extends MainView{
        void showProgress();
        void hideProgress();
        void failedCreatePum(String message);
        void successCreatePum();
    }

    interface ReqValidationPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void clearComposite();
        void createPumToServer(RequestModel requestModel);
    }
}
