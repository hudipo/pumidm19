package com.hudipo.pum_indomaret.networking;

import com.hudipo.pum_indomaret.model.login.LoginResponse;

import java.util.HashMap;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface APIService {

    @Multipart
    @POST("login")
    Single<Response<LoginResponse>> login(@PartMap HashMap<String, RequestBody> params);

}
