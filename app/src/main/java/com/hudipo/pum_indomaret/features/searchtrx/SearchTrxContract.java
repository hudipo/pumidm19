package com.hudipo.pum_indomaret.features.searchtrx;

import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;
import com.hudipo.pum_indomaret.view.MainView;

public interface SearchTrxContract {
    interface SearchTrxView extends MainView {
        void showProgress();
        void hideProgress();
        void setDataTrxType(TrxTypeResponse trxTypeResponse);
        void setDataEmpty();
        void showFailed(String message);
    }

    interface SearchTrxPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void clearComposite();
        void loadDataTrxType();
    }
}
