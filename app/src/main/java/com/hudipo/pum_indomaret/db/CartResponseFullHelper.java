package com.hudipo.pum_indomaret.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kotlin.jvm.Throws;

import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.ID_CART_RESPONSE_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.TABLE_CART_RESPONSE_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.TRX_NUMBER_FULL;

public class CartResponseFullHelper {

    private static final String DATABASE_TABLE_CART_RESPONSE_FULL = TABLE_CART_RESPONSE_FULL;
    private static CartResponseFullHelper INSTANCE;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private CartResponseFullHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static CartResponseFullHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new CartResponseFullHelper(context);
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

    public Cursor queryAll(){
        return database.query(
                DATABASE_TABLE_CART_RESPONSE_FULL,
                null,
                null,
                null,
                null,
                null,
                ID_CART_RESPONSE_FULL + " ASC",
                null
        );
    }


    public Long insert(ContentValues values){
        return database.insert(DATABASE_TABLE_CART_RESPONSE_FULL, null, values);
    }

    public int deleteByTrxNumber(int id){
        return database.delete(DATABASE_TABLE_CART_RESPONSE_FULL, TRX_NUMBER_FULL + " = " + id, null);
    }

    public int deleteById(int id){
        return database.delete(DATABASE_TABLE_CART_RESPONSE_FULL, ID_CART_RESPONSE_FULL + " = " + id, null);
    }

    public int update(int id, ContentValues values){
        return database.update(DATABASE_TABLE_CART_RESPONSE_FULL, values, ID_CART_RESPONSE_FULL + " = ?", new String[]{String.valueOf(id)});
    }
}
