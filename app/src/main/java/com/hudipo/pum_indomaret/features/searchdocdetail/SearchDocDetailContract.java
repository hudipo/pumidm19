package com.hudipo.pum_indomaret.features.searchdocdetail;

import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import com.hudipo.pum_indomaret.view.MainView;

public interface SearchDocDetailContract {
    interface SearchDocDetailView extends MainView{
        void showProgress();
        void hideProgress();
        void setDataDocDetail(DocDetailResponse docDetailResponse);
        void showDataEmpty();
        void showFailed(String message);
    }

    interface SearchDocDetailPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void clearComposite();
        void loadDataDocDetail(String docType);
    }
}
