package com.hudipo.pum_indomaret.features.searchdept;

import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.view.MainView;

public interface SearchDeptContract {
    interface SearchDeptPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void clearComposite();
        void loadDataDept();
    }

    interface SearchDeptView extends MainView{
        void showProgress();
        void hideProgress();
        void setDataDept(DepartmentResponse departmentResponse);
        void setDataEmpty();
        void showFailed(String message);
    }
}
