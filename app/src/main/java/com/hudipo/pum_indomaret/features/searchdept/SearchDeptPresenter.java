package com.hudipo.pum_indomaret.features.searchdept;

import android.util.Log;

import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.repository.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class SearchDeptPresenter implements SearchDeptContract.SearchDeptPresenterView<SearchDeptContract.SearchDeptView>{
    private CompositeDisposable composite = new CompositeDisposable();
    private SearchDeptContract.SearchDeptView view;
    private Repository repository;

    public SearchDeptPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void onAttach(SearchDeptContract.SearchDeptView view) {
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
    public void loadDataDept() {
        view.showProgress();
        repository.getDataDepartment(composite, new Repository.RepositoryCallback.LoadDataDept() {
            @Override
            public void onDataLoad(DepartmentResponse departmentResponse) {
                view.hideProgress();
                if (departmentResponse != null){
                    view.setDataDept(departmentResponse);
                }else {
                    view.setDataEmpty();
                }
            }

            @Override
            public void onDataError(Throwable throwable) {
                view.hideProgress();
                view.showFailed(throwable.getMessage());
            }
        });
    }
}
