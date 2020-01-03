package com.hudipo.pum_indomaret.features.searchstorecode;

import com.hudipo.pum_indomaret.model.storecode.StoreCodeItem;
import com.hudipo.pum_indomaret.view.MainView;

import java.util.List;

public interface SearchStoreCodeContract {
    interface SearchStoreCodeView extends MainView {
        void showProgress();
        void hideProgress();
        void setDataStoreCode(List<StoreCodeItem> storeCodeItems);
        void setDataEmpty();
        void showFailed(String message);
    }

    interface SearchStoreCodePresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void clearComposite();
        void loadDataStoreCode();
    }
}
