package com.hudipo.pum_indomaret.features.approval.view;

import com.hudipo.pum_indomaret.model.approval.ApprovalModel;
import com.hudipo.pum_indomaret.view.MainView;

import java.util.List;

public class ApprovalContract {

    public interface ApprovalPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void getData();
        void searchData(String query);
        void approve(List<ApprovalModel> approvalModels);
        void reject(List<ApprovalModel> approvalModels);
    }

    public interface ApprovalView extends MainView{
        void errorNotSelected(String message);
        void error(String message);
        void showLoading();
        void dismissLoading();
        void success(String message);
    }
}
