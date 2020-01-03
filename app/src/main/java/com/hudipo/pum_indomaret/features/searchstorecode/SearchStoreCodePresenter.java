package com.hudipo.pum_indomaret.features.searchstorecode;

import com.hudipo.pum_indomaret.model.storecode.StoreCodeResponse;
import com.hudipo.pum_indomaret.repository.RepositoryResponse;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import io.reactivex.disposables.CompositeDisposable;

public class SearchStoreCodePresenter implements SearchStoreCodeContract.SearchStoreCodePresenterView<SearchStoreCodeContract.SearchStoreCodeView> {

    private SearchStoreCodeContract.SearchStoreCodeView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private RepositoryResponse repositoryResponse;
    private HawkStorage hawkStorage;

    SearchStoreCodePresenter(RepositoryResponse repositoryResponse, HawkStorage hawkStorage) {
        this.repositoryResponse = repositoryResponse;
        this.hawkStorage = hawkStorage;
    }

    @Override
    public void onAttach(SearchStoreCodeContract.SearchStoreCodeView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        if (mView != null){
            mView = null;
            composite.dispose();
        }
    }

    @Override
    public void clearComposite() {
        composite.clear();
    }

    @Override
    public void loadDataStoreCode() {
        mView.showProgress();
        repositoryResponse.setHawkStorage(hawkStorage);
        repositoryResponse.getDataStoreCode(composite, new RepositoryResponse.RepositoryResponseCallback.LoadDataStoreCode() {
            @Override
            public void onDataLoad(StoreCodeResponse storeCodeResponse) {
                mView.hideProgress();
                if (!storeCodeResponse.isError()){
                    if (storeCodeResponse.getData().size() > 0 && storeCodeResponse.getData() != null){
                        mView.setDataStoreCode(storeCodeResponse.getData());
                    }else {
                        mView.setDataEmpty();
                    }
                }
            }

            @Override
            public void onDataError(String message) {
                mView.hideProgress();
                mView.showFailed(message);
            }
        });
    }
}
