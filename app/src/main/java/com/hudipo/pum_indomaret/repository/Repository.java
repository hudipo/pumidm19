package com.hudipo.pum_indomaret.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import com.hudipo.pum_indomaret.model.login.LoginResponse;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.networking.RetrofitClient;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.Key;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class Repository {

    private HawkStorage hawkStorage;

    public void setHawkStorage(HawkStorage hawkStorage) {
        this.hawkStorage = hawkStorage;
    }

    public interface RepositoryCallback{
        interface LoadDataDept{
            void onDataLoad(DepartmentResponse departmentResponse);
            void onDataError(Throwable throwable);
        }

        interface LoadDataTrxType{
            void onDataLoad(TrxTypeResponse trxTypeResponse);
            void onDataError(Throwable throwable);
        }

        interface LoadDataDocDetail{
            void onDataLoad(DocDetailResponse docDetailResponse);
            void onDataError(String message);
        }
    }

    public void getDataDepartment(CompositeDisposable composite, RepositoryCallback.LoadDataDept callback){

        composite.add(new ApiServices().getApiPumServices()
                .getDepartment(hawkStorage.getUserData().getOrgId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(departmentResponse -> {
                    if (departmentResponse != null){
                        callback.onDataLoad(departmentResponse);
                    }
                }, callback::onDataError));
    }

    public void getDataTrxType(CompositeDisposable composite, RepositoryCallback.LoadDataTrxType callback){
        composite.add(new ApiServices().getApiPumServices()
            .getTrxType()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(trxTypeResponse -> {
                if (trxTypeResponse != null){
                    callback.onDataLoad(trxTypeResponse);
                }
            }, callback::onDataError));
    }

    static public ArrayList<String> getDataDocumentType(Context context){
        ArrayList<String> documentTypeList = new ArrayList<>();
        documentTypeList.add(context.getString(R.string.doc_type));
        documentTypeList.add("-");
        documentTypeList.add("SP");
        documentTypeList.add("PP");
        documentTypeList.add("PO");
        return documentTypeList;
    }

    static public ArrayList<String> getDataStatusApproval(Context context){
        ArrayList<String> documentTypeList = new ArrayList<>();
        documentTypeList.add("---");
        documentTypeList.add("Approved");
        documentTypeList.add("Rejected");
        return documentTypeList;
    }

    public void getDataDocDetail(CompositeDisposable composite, String docType, RepositoryCallback.LoadDataDocDetail callback){
        composite.add(new ApiServices().getApiPumServices()
            .getDocDetail(docType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                if (response.isSuccessful()){
                    DocDetailResponse docDetailResponse = response.body();
                    if (docDetailResponse != null){
                        if (!docDetailResponse.isError()){
                            callback.onDataLoad(docDetailResponse);
                        }
                    }
                }else {
                    Converter<ResponseBody,DocDetailResponse> errorConverter =
                            RetrofitClient.client().responseBodyConverter(DocDetailResponse.class, new Annotation[0]);
                    DocDetailResponse errorResponse;
                    try {
                        if (response.errorBody() != null){
                            errorResponse = errorConverter.convert(response.errorBody());

                            if (errorResponse != null){
                                callback.onDataError(errorResponse.getMessage());
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }, throwable -> {
                callback.onDataError(throwable.getMessage());
            }));
    }

    static public ArrayList<OptionItem> getDataChooseFile(){
        ArrayList<OptionItem> optionUploadFiles = new ArrayList<>();
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_CAMERA, R.drawable.camera, "Camera", "camera"));
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_GALLERY, R.drawable.gallery, "Gallery", "gallery"));
        optionUploadFiles.add(new OptionItem(Key.KEY_UPLOAD_FILE_WITH_FILES, R.drawable.files, "Files", "files"));

        return optionUploadFiles;
    }
}
