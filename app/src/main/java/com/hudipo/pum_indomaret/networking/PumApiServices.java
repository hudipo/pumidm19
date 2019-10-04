package com.hudipo.pum_indomaret.networking;

import com.hudipo.pum_indomaret.features.status.model.FilterStatusModel;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;
import com.hudipo.pum_indomaret.model.login.LoginResponse;
import com.hudipo.pum_indomaret.model.register.RegisterResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface PumApiServices {

    @Multipart
    @POST("login")
    Observable<LoginResponse> login(@PartMap HashMap<String, RequestBody> params);

    @Multipart
    @POST("register")
    Observable<RegisterResponse> register(@PartMap HashMap<String, RequestBody> params);

    @POST("historycreatepum")
    Observable<StatusResponse> getStatusListFromNetwork(@Body int emp_id);

    @POST("filterhistorycreatepum")
    Observable<StatusResponse> getFilteredStatusListFromNetwork(@Body FilterStatusModel filterStatusModel);

}
