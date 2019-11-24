package com.hudipo.pum_indomaret.features.searchtrx;

import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;
import com.hudipo.pum_indomaret.repository.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class SearchTrxPresenter implements SearchTrxContract.SearchTrxPresenterView<SearchTrxContract.SearchTrxView> {

    private SearchTrxContract.SearchTrxView mView;
    private Repository repository;
    private CompositeDisposable composite = new CompositeDisposable();

    SearchTrxPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void onAttach(SearchTrxContract.SearchTrxView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.dispose();
    }

    @Override
    public void clearComposite() {
        composite.clear();
    }

    @Override
    public void loadDataTrxType() {
        mView.showProgress();
        repository.getDataTrxType(composite, new Repository.RepositoryCallback.LoadDataTrxType() {
            @Override
            public void onDataLoad(TrxTypeResponse trxTypeResponse) {
                mView.hideProgress();
                if (trxTypeResponse != null){
                    if (!trxTypeResponse.isError()){
                        mView.setDataTrxType(trxTypeResponse);
                    }
                }else {
                    mView.setDataEmpty();
                }
            }

            @Override
            public void onDataError(Throwable throwable) {
                mView.hideProgress();
                mView.showFailed(throwable.getMessage());
            }
        });
    }
}
