package com.hudipo.pum_indomaret.utils;

import android.content.Context;

import com.hudipo.pum_indomaret.model.login.User;
import com.orhanobut.hawk.Hawk;

public class HawkStorage {
    private static final String USER_DATA = "USER_DATA";
    private static final String IS_LOGIN = "IS_LOGIN";

    public HawkStorage(Context context) {
        Hawk.init(context).build();
    }

    public void setUserData(User userData){
        Hawk.put(USER_DATA, userData);
    }

    public User getUserData(){
        return Hawk.get(USER_DATA);
    }

    public void setLogin(boolean isLogin){
        Hawk.put(IS_LOGIN, isLogin);
    }

    public boolean getLogin(){
        return Hawk.get(IS_LOGIN, false);
    }

}
