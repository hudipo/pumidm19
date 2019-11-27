package com.hudipo.pum_indomaret.features.approval.view;

import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.view.MainView;

import java.util.List;

public class ApprovalHistoryContract {

    public interface ApprovalHistoryPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void getData();
        void searchData(String query);
        void approve(List<ApprovalListModel> approvalModels);
        void reject(List<ApprovalListModel> approvalModels);
    }

    public interface ApprovalHistoryView extends MainView{
        void showData(List<ApprovalListModel> approvalModels);
        void errorNotSelected(String message);
        void error(String message);
        void showLoading();
        void dismissLoading();
        void success(String message);
    }
}
