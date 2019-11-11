package com.hudipo.pum_indomaret.networking;

public class ApiServices {

    public PumApiServices getApiPumServices(){
        return RetrofitClient
                .client()
                .create(PumApiServices.class);
    }
}
