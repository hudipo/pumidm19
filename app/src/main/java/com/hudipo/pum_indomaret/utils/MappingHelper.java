package com.hudipo.pum_indomaret.utils;

import android.database.Cursor;

import com.google.gson.Gson;
import com.hudipo.pum_indomaret.model.submitresponse.SubmitResponseItem;

import java.util.ArrayList;

import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.ID_CART_RESPONSE_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.SUBMIT_RESPONSE_ITEM_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.TRX_NUMBER_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.ID_CART_RESPONSE_PARTIAL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.SUBMIT_RESPONSE_ITEM_PARTIAL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.TRX_NUMBER_PARTIAL;

public class MappingHelper {

    public static ArrayList<SubmitResponseItem> mapToArrayResponseFull(Cursor cursor, String numberTrx){
        ArrayList<SubmitResponseItem> submitResponseItems = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_CART_RESPONSE_FULL));
            String submitResponse = cursor.getString(cursor.getColumnIndexOrThrow(SUBMIT_RESPONSE_ITEM_FULL));
            String trxNumber = cursor.getString(cursor.getColumnIndexOrThrow(TRX_NUMBER_FULL));
            if (submitResponse != null && trxNumber != null){
                if (numberTrx.trim().equals(trxNumber.trim())){
                    SubmitResponseItem responseItem = new Gson().fromJson(submitResponse, SubmitResponseItem.class);
                    responseItem.setId(id);
                    submitResponseItems.add(responseItem);
                }
            }
        }
        return submitResponseItems;
    }

    public static ArrayList<SubmitResponseItem> mapToArrayResponsePartial(Cursor cursor, String numberTrx){
        ArrayList<SubmitResponseItem> submitResponseItems = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_CART_RESPONSE_PARTIAL));
            String submitResponse = cursor.getString(cursor.getColumnIndexOrThrow(SUBMIT_RESPONSE_ITEM_PARTIAL));
            String trxNumber = cursor.getString(cursor.getColumnIndexOrThrow(TRX_NUMBER_PARTIAL));
            if (submitResponse != null && trxNumber != null){
                if (numberTrx.trim().equals(trxNumber.trim())){
                    SubmitResponseItem responseItem = new Gson().fromJson(submitResponse, SubmitResponseItem.class);
                    responseItem.setId(id);
                    submitResponseItems.add(responseItem);
                }
            }
        }
        return submitResponseItems;
    }
}
