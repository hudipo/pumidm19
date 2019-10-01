package com.hudipo.pum_indomaret.networking;

import com.hudipo.pum_indomaret.BuildConfig;

public class ApiServices {

    public PumApiServices getLoginServices(){
        return RetrofitClient
                .client(BuildConfig.BASE_URL)
                .create(PumApiServices.class);
    }
}
