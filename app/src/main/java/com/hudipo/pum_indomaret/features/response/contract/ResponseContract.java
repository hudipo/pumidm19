package com.hudipo.pum_indomaret.features.response.contract;

import com.hudipo.pum_indomaret.model.response.DataResponse;
import com.hudipo.pum_indomaret.view.MainView;

public interface ResponseContract {
    interface ResponseView extends MainView{
        void showProgress();
        void hideProgress();
        void showDataEmpty();
        void showError(String message);
        void setDataResponse(DataResponse getDataResponse);
    }

    interface ResponsePresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void clearComposite();
        void loadDataResponse();
    }
}
