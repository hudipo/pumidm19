package com.hudipo.pum_indomaret.utils;

import android.content.Context;

import com.hudipo.pum_indomaret.model.login.UserData;
import com.orhanobut.hawk.Hawk;

public class HawkStorage {
    private Context context;
    private static final String USER_DATA = "USER_DATA";
    private static final String IS_LOGIN = "IS_LOGIN";

    public HawkStorage(Context context) {
        this.context = context;
    }

    public void setUserData(UserData userData){
        Hawk.put(USER_DATA, userData);
    }

    public UserData getUserData(){
        return Hawk.get(USER_DATA);
    }

    public void setLogin(boolean isLogin){
        Hawk.put(IS_LOGIN, isLogin);
    }

    public boolean getLogin(){
        return Hawk.get(IS_LOGIN);
    }

}
