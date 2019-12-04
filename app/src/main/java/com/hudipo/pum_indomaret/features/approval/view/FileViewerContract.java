package com.hudipo.pum_indomaret.features.approval.view;

import com.hudipo.pum_indomaret.view.MainView;

public class FileViewerContract {

    public interface FileViewerPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void setData();
        void download();
    }

    public interface FileViewerView extends MainView{
        void showImage();
        void showPdf();
        void showAlert(String message);
        void showLoading();
        void dismissLoading();
    }
}
