package com.hudipo.pum_indomaret.features.approval.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.FileViewerActivity;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalContract;
import com.hudipo.pum_indomaret.features.approval.view.FileViewerContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_URL;

public class FileViewerPresenter implements FileViewerContract.FileViewerPresenterView<FileViewerContract.FileViewerView> {
    private Context context;
    private FileViewerContract.FileViewerView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private String url="";
    private int pumTrxId;

    public FileViewerPresenter(Context context, String url, int pumTrxId) {
        this.context = context;
        this.url = url;
        this.pumTrxId = pumTrxId;
    }

    @Override
    public void onAttach(FileViewerContract.FileViewerView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.clear();
    }

    @Override
    public void setData() {
        //check extensi file
        String extension = url.substring(url.lastIndexOf(".") + 1);
        if(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")
                || extension.equalsIgnoreCase("png")){
            mView.showImage();
        }else if(extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("doc")
                || extension.equalsIgnoreCase("docx")){
            mView.showPdf();
        }else {
            mView.showAlert(context.getString(R.string.err_server));
        }
    }

    @Override
    public void download() {
        mView.showLoading();
        composite.add(new ApiServices().getApiPumServices().downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful() && response.body()!=null){
                        saveFile(response.body());
                    }else {
                        mView.showAlert(context.getString(R.string.download_failed));
                        mView.dismissLoading();
                    }

                }, throwable -> {
                    mView.showAlert(context.getString(R.string.download_failed));
                    mView.dismissLoading();
                })
        );
    }

    private void saveFile(ResponseBody body) {
        try {
            String imageFileName = pumTrxId+"_";
            imageFileName += url.substring(url.lastIndexOf("/") + 1);
            File storageDir = new File(Environment.getExternalStorageDirectory(),
                    context.getString(R.string.app_name));
            boolean success = true;
            if (!storageDir.exists()) {
                success = storageDir.mkdirs();
            }
            if (success) {
                File file = new File(storageDir, imageFileName);
                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    byte[] fileReader = new byte[4096];
                    //ini buat progressbar
//                    long fileSize = body.contentLength();
//                    long fileSizeDownloaded = 0;
                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(file);

                    while (true) {
                        int read = inputStream.read(fileReader);

                        if (read == -1) {
                            break;
                        }

                        outputStream.write(fileReader, 0, read);

                        //ini buat progressbar
//                        fileSizeDownloaded += read;

                    }

                    outputStream.flush();
                    mView.dismissLoading();
                    mView.showAlert(context.getString(R.string.download_success));
                } catch (IOException e) {
                    mView.dismissLoading();
                    mView.showAlert(context.getString(R.string.download_failed));
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            }
        } catch (IOException e) {
            mView.dismissLoading();
            mView.showAlert(context.getString(R.string.download_failed));
        }
    }
}
