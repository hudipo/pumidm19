package com.hudipo.pum_indomaret.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kotlin.jvm.Throws;

import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.ID_CART_RESPONSE_PARTIAL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.TABLE_CART_RESPONSE_PARTIAL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.TRX_NUMBER_PARTIAL;

public class CartResponsePartialHelper {

    private static final String DATABASE_TABLE_CART_RESPONSE_PARTIAL = TABLE_CART_RESPONSE_PARTIAL;
    private static CartResponsePartialHelper INSTANCE;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private CartResponsePartialHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static CartResponsePartialHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new CartResponsePartialHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    @Throws(exceptionClasses = SQLException.class)
    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public Cursor queryAllCartResponseFull(){
        return database.query(
                DATABASE_TABLE_CART_RESPONSE_PARTIAL,
                null,
                null,
                null,
                null,
                null,
                ID_CART_RESPONSE_PARTIAL + " ASC",
                null
        );
    }


    public Long insert(ContentValues values){
        return database.insert(DATABASE_TABLE_CART_RESPONSE_PARTIAL, null, values);
    }

    public int deleteByTrxNumber(int id){
        return database.delete(DATABASE_TABLE_CART_RESPONSE_PARTIAL, TRX_NUMBER_PARTIAL + " = " + id, null);
    }

    public int deleteById(int id){
        return database.delete(DATABASE_TABLE_CART_RESPONSE_PARTIAL, ID_CART_RESPONSE_PARTIAL + " = " + id, null);
    }

    public int update(int id, ContentValues values){
        return database.update(DATABASE_TABLE_CART_RESPONSE_PARTIAL, values, ID_CART_RESPONSE_PARTIAL + " = ?", new String[]{String.valueOf(id)});
    }
}
