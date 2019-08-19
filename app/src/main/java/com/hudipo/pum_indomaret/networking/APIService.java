package com.hudipo.pum_indomaret.networking;

import com.hudipo.pum_indomaret.model.login.LoginResponse;

import java.util.HashMap;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface APIService {

    @FormUrlEncoded
    @POST("login")
    Single<LoginResponse> login(@PartMap HashMap<String, RequestBody> params);

}
