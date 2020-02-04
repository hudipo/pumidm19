package com.hudipo.pum_indomaret.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    static public String getRealPathImageFromURI(Context context, Uri uri) {
        String path = "";
        if (context.getContentResolver() != null) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    static public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    static public String getRealPathDocumentFromUri(Context context, Uri uri){
        String path = "";
        if (context.getContentResolver() != null) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            try (Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String mediaPath = cursor.getString(columnIndex);
                    Log.d("coba", "path : "+mediaPath);
                    path = mediaPath;
                }
            }
        }

        return path;
    }

    static public String convertLongToCurrency(long amount){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(amount);
    }
}
