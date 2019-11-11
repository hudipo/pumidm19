package com.hudipo.pum_indomaret.data;

import android.content.Context;
import android.content.res.TypedArray;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.home.HomeModel;

import java.util.ArrayList;

public class Data {
     static public ArrayList<HomeModel> dataHome(Context context){
        ArrayList<HomeModel> list = new ArrayList<>();
        String[] title = context.getResources().getStringArray(R.array.data_title_home);
        TypedArray image = context.getResources().obtainTypedArray(R.array.data_image);
        for (int i = 0; i < title.length; i++){
            list.add(new HomeModel(
                    i,
                    image.getResourceId(i, 0),
                    title[i]
            ));
        }
        return list;
    }
}
