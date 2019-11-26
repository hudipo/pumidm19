package com.hudipo.pum_indomaret.networking;

import com.hudipo.pum_indomaret.model.createpum.CreatePumResponse;
import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import com.hudipo.pum_indomaret.model.login.LoginResponse;
import com.hudipo.pum_indomaret.model.register.RegisterResponse;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface PumApiServices {

    @Multipart
    @POST("login")
    Observable<Response<LoginResponse>> login(@PartMap HashMap<String, RequestBody> params);

    @Multipart
    @POST("registerpin")
    Observable<Response<RegisterResponse>> register(@PartMap HashMap<String, RequestBody> params);

    @FormUrlEncoded
    @POST("getdept")
    Observable<DepartmentResponse> getDepartment(@Field("org_id") int orgId);

    @FormUrlEncoded
    @POST("getdocdetail")
    Observable<DocDetailResponse> getDocDetail(@Field("doc_type") String docType);

    @GET("gettrxtype")
    Observable<TrxTypeResponse> getTrxType();

    @Multipart
    @POST("createpum")
    Observable<CreatePumResponse> createPum(@PartMap HashMap<String, RequestBody> params);

    @Multipart
    @POST("historycreatepum")
    Observable<StatusResponse> getStatusListFromNetwork(@PartMap HashMap<String, RequestBody> params);

}
