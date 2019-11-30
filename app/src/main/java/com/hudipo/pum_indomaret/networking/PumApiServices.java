package com.hudipo.pum_indomaret.networking;

import com.hudipo.pum_indomaret.model.approval.ApprovalListResponse;
import com.hudipo.pum_indomaret.model.approval.detail.ApprovalDetailResponse;
import com.hudipo.pum_indomaret.model.approval.history.ApprovalHistoryListResponse;
import com.hudipo.pum_indomaret.model.createpum.CreatePumResponse;
import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import com.hudipo.pum_indomaret.model.login.LoginResponse;
import com.hudipo.pum_indomaret.model.pin.PinResponse;
import com.hudipo.pum_indomaret.model.register.RegisterResponse;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Observable<Response<DocDetailResponse>> getDocDetail(@Field("doc_type") String docType);

    @GET("gettrxtype")
    Observable<TrxTypeResponse> getTrxType();

    @Multipart
    @POST("createpum")
    Observable<Response<CreatePumResponse>> createPum(
            @PartMap HashMap<String, RequestBody> params,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("historycreatepum")
    Observable<StatusResponse> getStatusListFromNetwork(@PartMap HashMap<String, RequestBody> params);

    @FormUrlEncoded
    @POST("testpin")
    Observable<Response<PinResponse>> checkPin(@Field("emp_id") int empId, @Field("pin") String pin);

    @POST("filterhistorycreatepum")
    Observable<StatusResponse> getFilteredStatusListFromNetwork(@Field("emp_id") int emp_id,
                                                                @Field("start_date") String start_date,
                                                                @Field("finish_date") String finish_date,
                                                                @Field("status") String status);
    //APPROVAL
    @FormUrlEncoded
    @POST("listapproval")
    Observable<ApprovalListResponse> getListApproval(@Field("emp_id") int emp_id);

    @Multipart
    @POST("historyapprovepum")
    Observable<ApprovalHistoryListResponse> getListHistoryApproval(@PartMap HashMap<String, RequestBody> params);

    @FormUrlEncoded
    @POST("detailpum")
    Observable<ApprovalDetailResponse> getDetailApproval(@Field("pum_trx_id") int pumTrxId);
}
