package com.hudipo.pum_indomaret.features.searchdocdetail;

import android.util.Log;

import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import com.hudipo.pum_indomaret.repository.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class SearchDocDetailPresenter implements SearchDocDetailContract.SearchDocDetailPresenterView<SearchDocDetailContract.SearchDocDetailView>{

    private CompositeDisposable composite = new CompositeDisposable();
    private SearchDocDetailContract.SearchDocDetailView view;
    private Repository repository;

    SearchDocDetailPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void onAttach(SearchDocDetailContract.SearchDocDetailView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        if (this.view != null){
            this.view = null;
            composite.dispose();
        }
    }

    @Override
    public void clearComposite() {
        composite.clear();
    }

    @Override
    public void loadDataDocDetail(String docType) {
        view.showProgress();
        repository.getDataDocDetail(composite, docType, new Repository.RepositoryCallback.LoadDataDocDetail(){

            @Override
            public void onDataLoad(DocDetailResponse docDetailResponse) {
                view.hideProgress();
                if (!docDetailResponse.getDocData().getData().isEmpty()){
                    view.setDataDocDetail(docDetailResponse);
                }else {

                    view.showDataEmpty();
                }
            }

            @Override
            public void onDataError(String message) {
                view.hideProgress();
                view.showFailed(message);
            }
        });

    }
}
