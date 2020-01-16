package com.hudipo.pum_indomaret.features.response.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.db.CartResponsePartialHelper;
import com.hudipo.pum_indomaret.adapter.ResponseCartPartialAdapter;
import com.hudipo.pum_indomaret.helper.CustomLoadingProgress;
import com.hudipo.pum_indomaret.model.createpum.CreatePumResponse;
import com.hudipo.pum_indomaret.model.submitresponse.SubmitResponseItem;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.networking.RetrofitClient;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.MappingHelper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ResponseCartPartialActivity extends AppCompatActivity {

    @BindView(R.id.rvResponseCart)
    RecyclerView rvResponseCart;
    @BindView(R.id.tvIsEmpty)
    TextView tvIsEmpty;


    private CustomLoadingProgress loadingProgress = new CustomLoadingProgress();
    private CartResponsePartialHelper helper = CartResponsePartialHelper.getInstance(this);
    private ArrayList<SubmitResponseItem> submitResponseItems;

    public static final String EXTRA_TRX_NUMBER = "extra_trx_number";
    public static final String EXTRA_PUM_TRX_ID = "extra_pum_trx_id";

    private CompositeDisposable composite;
    private int pumTrxId;
    private String trxNumber;
    private HawkStorage hawkStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_cart_partial);
        ButterKnife.bind(this);

        loadComponent();
        openHelperDB();
        getDataIntent();
    }

    private void loadComponent() {
        composite = new CompositeDisposable();
        hawkStorage = new HawkStorage(this);
    }

    private void setAdapterResponseCart(ArrayList<SubmitResponseItem> submitResponseItems) {
        ResponseCartPartialAdapter responseCartPartialAdapter = new ResponseCartPartialAdapter();
        responseCartPartialAdapter.setSubmitResponseItems(submitResponseItems);

        rvResponseCart.setLayoutManager(new LinearLayoutManager(this));
        rvResponseCart.setHasFixedSize(true);
        rvResponseCart.setAdapter(responseCartPartialAdapter);

        responseCartPartialAdapter.removeItemFromAdapter((position, idSubmitResponse) -> {
            int result = helper.deleteById(idSubmitResponse);
            if (result > 0){
                responseCartPartialAdapter.removeItem(position);
                rvResponseCart.smoothScrollToPosition(position);
                Toast.makeText(this, "item successfully deleted", Toast.LENGTH_SHORT).show();

                if (responseCartPartialAdapter.getItemCount() == 0){
                    showDataEmpty();
                }
            }else{
                Toast.makeText(this, "item failed deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openHelperDB() {
        helper.open();
    }

    private void showDataEmpty(){
        rvResponseCart.setVisibility(View.INVISIBLE);
        tvIsEmpty.setVisibility(View.VISIBLE);
    }

    private void showData(){
        rvResponseCart.setVisibility(View.VISIBLE);
        tvIsEmpty.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnAddResponse)
    void btnAddResponse(){
        finish();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }

    @OnClick(R.id.btnSubmit)
    void btnSubmit(){
        if (submitResponseItems.size() > 0){
            showProgress();
            submitDataToServer();
        }else {
            Toast.makeText(this, "Your data is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitDataToServer() {
        ArrayList<MultipartBody.Part> listImages = new ArrayList<>();
        ArrayList<RequestBody> listTrxTypeId = new ArrayList<>();
        ArrayList<RequestBody> listDescription= new ArrayList<>();
        ArrayList<RequestBody> listStoreCode = new ArrayList<>();
        ArrayList<RequestBody> listAmount= new ArrayList<>();

        for (SubmitResponseItem row: submitResponseItems){
            RequestBody requestTrxTypeId = convertToRequestBody(String.valueOf(row.getTrxTypeId()));
            RequestBody requestDescription = convertToRequestBody(row.getDescription());
            RequestBody requestStoreCode = convertToRequestBody(row.getStoreCode());
            RequestBody requestAmount = convertToRequestBody(row.getAmount());

            listTrxTypeId.add(requestTrxTypeId);
            listDescription.add(requestDescription);
            listStoreCode.add(requestStoreCode);
            listAmount.add(requestAmount);

            listImages.add(convertToBodyImageFile(row.getRealPath(), row.getTypeFile()));
        }

        RequestBody requestEmpId = convertToRequestBody(String.valueOf(hawkStorage.getUserData().getEmpId()));
        RequestBody requestOrgId = convertToRequestBody(String.valueOf(hawkStorage.getUserData().getOrgId()));
        RequestBody requestPumTrxId = convertToRequestBody(String.valueOf(pumTrxId));
        RequestBody requestKode = convertToRequestBody(String.valueOf(1));


        composite.add(new ApiServices().getApiPumServices()
                .submitResponse(
                        requestEmpId,
                        requestPumTrxId,
                        requestKode,
                        requestOrgId,
                        listTrxTypeId,
                        listDescription,
                        listStoreCode,
                        listAmount,
                        listImages)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    dismissLoading();
                    if (response.isSuccessful()){
                        CreatePumResponse submitPumResponse = response.body();
                        if (submitPumResponse != null && !submitPumResponse.isError()) {
                            helper.deleteByTrxNumber(Integer.valueOf(trxNumber));
                            startActivity(new Intent(this, PartialResponseSentActivity.class));
                            finish();
                        }
                    }else {
                        dismissLoading();
                        Converter<ResponseBody, CreatePumResponse> errorConverter =
                                RetrofitClient.client().responseBodyConverter(CreatePumResponse.class, new Annotation[0]);
                        CreatePumResponse errorResponse;
                        try {
                            if (response.errorBody() != null){
                                errorResponse = errorConverter.convert(response.errorBody());

                                if (errorResponse != null){
                                    Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }, throwable -> {
                    dismissLoading();
                    Toast.makeText(this, "error: "+throwable.getMessage(), Toast.LENGTH_LONG).show();
                }));
    }




    private void showProgress() {
        loadingProgress.showCustomDialog(this);
    }


    public void dismissLoading() {
        loadingProgress.closeCustomDialog();
    }

    private RequestBody convertToRequestBody(String value){
        return RequestBody.create(value, MediaType.parse("text/plain"));
    }

    @NotNull
    private MultipartBody.Part convertToBodyImageFile(String realPath, String typeFile) {
        File file = new File(realPath);

        RequestBody requestFileUri = RequestBody.create(file, MediaType.parse(Objects.requireNonNull(typeFile)));
        return MultipartBody.Part.createFormData("image[]", file.getName(), requestFileUri);
    }

//    @OnClick(R.id.btnCancel)
//    void btnCancel(){
//        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
//    }

    private void getDataIntent() {
        if (getIntent() != null){
            trxNumber = getIntent().getStringExtra(EXTRA_TRX_NUMBER);
            pumTrxId = getIntent().getIntExtra(EXTRA_PUM_TRX_ID, 0);
            if (trxNumber != null){
                getDataDB(trxNumber);
            }
        }
    }

    private void getDataDB(String trxNumber) {
        Cursor cursor = helper.queryAllCartResponseFull();
        submitResponseItems = MappingHelper.mapToArrayResponsePartial(cursor, trxNumber);

        if (submitResponseItems != null && submitResponseItems.size() > 0){
            setAdapterResponseCart(submitResponseItems);
            showData();
        }else {
            showDataEmpty();
        }
    }
}
