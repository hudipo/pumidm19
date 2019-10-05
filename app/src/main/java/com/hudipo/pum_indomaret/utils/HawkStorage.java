package com.hudipo.pum_indomaret.utils;

import android.content.Context;

import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.model.login.User;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;
import com.orhanobut.hawk.Hawk;

public class HawkStorage {
    private static final String USER_DATA = "USER_DATA";
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String DEPARTMENT_DATA = "DEPARTMENT_DATA";
    private static final String TRX_TYPE_DATA = "TRX_TYPE_DATA";

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

    public void setDepartmentData(DepartmentResponse departmentData){
        Hawk.put(DEPARTMENT_DATA, departmentData);
    }

    public DepartmentResponse getDepartment(){
        return Hawk.get(DEPARTMENT_DATA);
    }

    public void setTrxTypeData(TrxTypeResponse trxTypeData){
        Hawk.put(TRX_TYPE_DATA, trxTypeData);
    }

    public TrxTypeResponse getTrxTypeData(){
        return Hawk.get(TRX_TYPE_DATA);
    }

    public void deleteAll(){
        Hawk.deleteAll();
    }

}
