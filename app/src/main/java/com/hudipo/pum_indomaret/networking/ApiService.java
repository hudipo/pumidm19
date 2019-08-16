package com.hudipo.pum_indomaret.networking;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Observable<Object> login(@PartMap HashMap<String, RequestBody> params);


}
