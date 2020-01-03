package com.hudipo.pum_indomaret.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.ID_CART_RESPONSE_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.SUBMIT_RESPONSE_ITEM_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.TABLE_CART_RESPONSE_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponseFullColumns.TRX_NUMBER_FULL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.ID_CART_RESPONSE_PARTIAL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.SUBMIT_RESPONSE_ITEM_PARTIAL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.TABLE_CART_RESPONSE_PARTIAL;
import static com.hudipo.pum_indomaret.db.DatabaseContract.CartResponsePartialColumns.TRX_NUMBER_PARTIAL;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbpum";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_CART_RESPONSE_FULL = "CREATE TABLE " + TABLE_CART_RESPONSE_FULL +
            " (" + ID_CART_RESPONSE_FULL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SUBMIT_RESPONSE_ITEM_FULL + " TEXT NOT NULL, " +
            TRX_NUMBER_FULL + " TEXT NOT NULL)";
    private static final String SQL_CREATE_TABLE_CART_RESPONSE_PARTIAL = "CREATE TABLE " + TABLE_CART_RESPONSE_PARTIAL +
            " (" + ID_CART_RESPONSE_PARTIAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SUBMIT_RESPONSE_ITEM_PARTIAL + " TEXT NOT NULL, " +
            TRX_NUMBER_PARTIAL + " TEXT NOT NULL)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CART_RESPONSE_FULL);
        db.execSQL(SQL_CREATE_TABLE_CART_RESPONSE_PARTIAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_RESPONSE_FULL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_RESPONSE_PARTIAL);
    }
}
