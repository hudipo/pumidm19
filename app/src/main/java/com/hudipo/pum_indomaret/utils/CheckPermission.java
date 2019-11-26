package com.hudipo.pum_indomaret.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CheckPermission {

    static public Boolean checkPermissionCamera(Activity activity, int requestCode){
        boolean isPermission;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
            isPermission = false;
        }else {
            isPermission = true;
        }
        return isPermission;
    }

    static public Boolean checkPermissionStorage(Activity activity, int requestCode){
        boolean isPermission;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
            isPermission = false;
        }else {
            isPermission = true;
        }
        return isPermission;
    }
}
