package com.hudipo.pum_indomaret.utils;

import android.content.Context;
import android.content.Intent;

public class StartActivity{
    static public void goTo(Context context, Class mClass){
        context.startActivity(new Intent(context, mClass));
    }
}
