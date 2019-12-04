package com.hudipo.pum_indomaret.features.approval.view;

import com.hudipo.pum_indomaret.model.approval.history.ApprovalHistoryListModel;
import com.hudipo.pum_indomaret.view.MainView;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;

public class ApprovalHistoryContract {

    public interface ApprovalHistoryPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void getData(HashMap<String, RequestBody> params);
        void searchData(String query);
        void approve(List<ApprovalHistoryListModel> approvalModels);
        void reject(List<ApprovalHistoryListModel> approvalModels);
    }

    public interface ApprovalHistoryView extends MainView{
        void showData(List<ApprovalHistoryListModel> approvalModels);
        void error(String message);
        void showLoading();
        void dismissLoading();
    }
}
