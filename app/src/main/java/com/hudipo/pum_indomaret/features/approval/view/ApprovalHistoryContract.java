package com.hudipo.pum_indomaret.features.approval.view;

import com.hudipo.pum_indomaret.model.approval.ApprovalModel;
import com.hudipo.pum_indomaret.view.MainView;

import java.util.List;

public class ApprovalHistoryContract {

    public interface ApprovalHistoryPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void getData();
        void searchData(String query);
        void approve(List<ApprovalModel> approvalModels);
        void reject(List<ApprovalModel> approvalModels);
    }

    public interface ApprovalHistoryView extends MainView{
        void showData(List<ApprovalModel> approvalModels);
        void errorNotSelected(String message);
        void error(String message);
        void showLoading();
        void dismissLoading();
        void success(String message);
    }
}
